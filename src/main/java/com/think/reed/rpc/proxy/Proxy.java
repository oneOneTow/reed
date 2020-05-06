package com.think.reed.rpc.proxy;

import com.think.reed.core.medium.ReedRequest;
import com.think.reed.core.medium.ReedResponse;

public interface Proxy {

  ReedResponse doInvoke(ReedRequest reedRequest);
}
