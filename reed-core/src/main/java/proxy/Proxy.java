package proxy;


import medium.ReedRequest;
import medium.ReedResponse;

public interface Proxy {

  ReedResponse doInvoke(ReedRequest reedRequest);
}
