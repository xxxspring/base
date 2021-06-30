package com.lf.ec.common.base.fsm

import com.lf.ec.common.base.fsm.annotation.EnableFSMHandler
import com.lf.ec.common.base.fsm.annotation.HandleTransition
import org.apache.commons.io.FilenameUtils
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.w3c.dom.Document
import org.w3c.dom.Node
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.jvm.javaMethod

/**
 * 读取状态机配置
 */
class FSMReader {

    private val logger by lazy { LoggerFactory.getLogger(FSMReader::class.java) }

    fun read(location: String, context: ApplicationContext): Map<String, FSM> {
        val handlerMap = loadHandlers(context)
        return loadFSM(location, handlerMap)
    }

    private fun loadHandlers(context: ApplicationContext): Map<String, FSMTransition.Handler> {
        val map = mutableMapOf<String, FSMTransition.Handler>()

        // annotation handlers
        context.getBeansWithAnnotation(EnableFSMHandler::class.java).forEach { _, bean ->
            bean::class.declaredMemberFunctions.forEach { it ->
                val annotation = AnnotationUtils.findAnnotation(it.javaMethod!!, HandleTransition::class.java)
                if (annotation != null) {
                    map[annotation.id] = FSMTransition.Handler(it, bean)
                    logger.info("fsm transition handler ${annotation.id} => $it")
                }
            }
        }

        // interface handlers
        context.getBeansOfType(FSMHandler::class.java).forEach { _, bean ->
            val id = bean.id()
            val handler = bean::handleTransition
            map[id] = FSMTransition.Handler(handler, null)
            logger.info("fsm transition handler $id => $handler")
        }

        return map
    }

    private fun loadFSM(location: String, handlerMap: Map<String, FSMTransition.Handler>): Map<String, FSM> {
        val map = mutableMapOf<String, FSM>()
        val resources = PathMatchingResourcePatternResolver().getResources(location)
        resources.forEach { it ->
            val type = FilenameUtils.removeExtension(it.filename)
            logger.info("fsm $type @ ${it.uri}")
            map[type] = parseFSM(it.inputStream, handlerMap)
        }
        return map
    }

    private fun parseFSM(inputStream: InputStream, handlerMap: Map<String, FSMTransition.Handler>): FSM {
        val docBuilderFactory = DocumentBuilderFactory.newInstance()
        val docBuilder = docBuilderFactory.newDocumentBuilder()
        val doc = docBuilder.parse(inputStream)
        doc.documentElement.normalize()

        val events = parseEvents(doc)
        val eventMap = events.associateBy { it.name }

        val states = parseStates(doc)
        val stateMap = states.associateBy { it.name }

        val docs = doc.getElementsByTagName("state")
        for (i in 0 until docs.length) {
            val node = docs.item(i)
            val from = states[i]
            from.transitions = parseStateTransitions(node, from, stateMap, eventMap, handlerMap)
        }

        val entry = parseEntry(doc, stateMap)

        return FSM(states, entry)
    }

    private fun parseEvents(root: Document): List<FSMEvent> {
        logger.info("\tevents:")
        val events = mutableListOf<FSMEvent>()
        val docs = root.getElementsByTagName("event")
        for (i in 0 until docs.length) {
            val doc = docs.item(i)
            val name = doc.attributes.getNamedItem("name").textContent
            val title = doc.attributes.getNamedItem("title").textContent
            val event = FSMEvent(name, title)
            events.add(event)
            logger.info("\t\t$name ($title)")
        }
        return events
    }

    private fun parseStates(root: Document): List<FSMState> {
        logger.info("\tstates:")
        val states = mutableListOf<FSMState>()
        val docs = root.getElementsByTagName("state")
        for (i in 0 until docs.length) {
            val doc = docs.item(i)
            val name = doc.attributes.getNamedItem("name").textContent
            val title = doc.attributes.getNamedItem("title")?.textContent
            logger.info("\t\t$name ($title)")
            val values = parseStateValues(doc)
            val state = FSMState(name, title, values)
            states.add(state)
        }
        return states
    }

    private fun parseStateValues(node: Node): Map<String, Any?>? {
        val valuesNode = findChildNodeByName(node, "values") ?: return null
        val values = mutableMapOf<String, String>()
        for (i in 0 until valuesNode.childNodes.length) {
            val valueNode = valuesNode.childNodes.item(i)
            if (valueNode.nodeType == Node.TEXT_NODE) continue
            val key = valueNode.nodeName
            val value = valueNode.firstChild.nodeValue
            values[key] = value
            logger.info("\t\t\t$key => $value")
        }
        return values
    }

    private fun parseStateTransitions(node: Node, from: FSMState, stateMap: Map<String, FSMState>,
                                      eventMap: Map<String, FSMEvent>,
                                      handlerMap: Map<String, FSMTransition.Handler>): List<FSMTransition>? {
        logger.info("\t${from.name} transitions:")
        val transitionsNode = findChildNodeByName(node, "transitions") ?: return null
        val transitions = mutableListOf<FSMTransition>()

        for (i in 0 until transitionsNode.childNodes.length) {
            val transNode = transitionsNode.childNodes.item(i)
            if (transNode.attributes == null) continue

            val eventName = transNode.attributes.getNamedItem("event").textContent
            val event = eventMap[eventName] ?: throw RuntimeException("Missing fsm event $eventName")

            val toName = transNode.attributes.getNamedItem("to").textContent
            val to = stateMap[toName] ?: throw RuntimeException("Missing fsm state $toName")

            logger.info("\t\t${event.name} => ${to.name}")

            val handlers = parseTransitionHandlers(transNode, handlerMap)

            val transition = FSMTransition(event, from, to, handlers)
            transitions.add(transition)
        }
        return transitions
    }

    private fun parseTransitionHandlers(node: Node, handlerMap: Map<String, FSMTransition.Handler>): List<FSMTransition.Handler>? {
        logger.info("\t\t\thandlers:")
        val handlers = mutableListOf<FSMTransition.Handler>()
        for (i in 0 until node.childNodes.length) {
            val handlerNode = node.childNodes.item(i)
            if (handlerNode.attributes == null) continue
            val id = handlerNode.attributes.getNamedItem("id").textContent
            val handler = handlerMap[id] ?: throw RuntimeException("Missing fsm handler $id")
            handlers.add(handler)
            logger.info("\t\t\t\t$id")
        }
        return handlers
    }

    private fun parseEntry(root: Document, stateMap: Map<String, FSMState>): FSMState? {
        val docs = root.getElementsByTagName("fsm")
        val doc = docs.item(0)
        val entryName = doc.attributes.getNamedItem("entry").textContent
        val entry = stateMap[entryName]
        logger.info("\tentry: ${entry?.name}")
        return entry
    }

    private fun findChildNodeByName(node: Node, name: String): Node? {
        for (i in 0 until node.childNodes.length) {
            val child = node.childNodes.item(i)
            if (child.nodeName === name) return child
        }
        return null
    }
}