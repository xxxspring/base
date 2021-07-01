package io.github.xxxspring.base.client

interface CrudServiceClient<S, T, CT, UT> :
        RetrieveServiceClient<S, T>,
        BatchRetrieveServiceClient<S, T>,
        CreateServiceClient<CT, T>,
        UpdateServiceClient<UT, T>,
        DeleteServiceClient<S, T>,
        BatchDeleteServiceClient<S>,
        PageServiceClient<T>
