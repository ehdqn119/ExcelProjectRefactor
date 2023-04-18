package org.example.exception;


/**
 *  동일 Ip, PORT, Protocol 이 곂치는 경우 Exception 을 발생시킵니다.
 */
public class DuplicatePolicyException extends BusinessException {
    public DuplicatePolicyException(String msg) {
        super(msg);
    }
}
