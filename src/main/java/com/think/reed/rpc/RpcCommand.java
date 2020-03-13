package com.think.reed.rpc;

import lombok.Data;

/**
 * @author jgs
 * @date 2020/3/13 17:05
 */
@Data
public abstract class RpcCommand {

  private byte classLen;
  private byte headerLen;
  private int ContentLen;
  private byte[] className;
  private byte[] head;
  private byte[] content;
}
