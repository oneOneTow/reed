package com.think.reed.rpc;

import lombok.Data;

/**
 * @author jgs
 * @date 2020/3/13 17:25
 */
@Data
public class RpcResponseCommand extends RpcCommand {

  private byte ResponseCode;

}
