package com.think.reed.rpc.proxy;

import com.think.reed.rpc.ReedRequest;
import com.think.reed.rpc.ReedResponse;

public interface Proxy {

    ReedResponse doInvoke(ReedRequest reedRequest);

    ReedRequest buildRequest();
}
