package com.dbc.vemserback.ecommerce.exception;

public class BusinessRuleException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4289109216519725837L;

	public BusinessRuleException(String mensagem){
        super(mensagem);
    }
}
