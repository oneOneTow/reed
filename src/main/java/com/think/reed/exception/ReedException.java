package com.think.reed.exception;

import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 * @author jgs
 * @date 2020/3/19 22:23
 */
public class ReedException  extends NestableRuntimeException {
  public ReedException(String errorCode){
    super(errorCode);
  }

  public ReedException(String errorCode, Throwable cause){
    super(errorCode, cause);
  }

  public ReedException(String errorCode, String errorDesc){
    super(errorCode + ":" + errorDesc);
  }

  public ReedException(String errorCode, String errorDesc, Throwable cause){
    super(errorCode + ":" + errorDesc, cause);
  }

  public ReedException(Throwable cause){
    super(cause);
  }

}
