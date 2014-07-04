/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.concurrenttest;

import java.security.Principal;
import javax.ejb.EJBContext;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Helge Waastad <helge@waastad.org>
 */
public class RunnableTest implements Runnable {

    private Principal expectedPrincipal;

    public void setExpectedPrincipal(Principal expectedPrincipal) {
        this.expectedPrincipal = expectedPrincipal;
    }

    public RunnableTest() {
    }

    @Override
    public void run() {
        System.out.println("Runnable process");
        try {
            Thread.currentThread().getContextClassLoader().loadClass(this.getClass().getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        final InitialContext initialContext;
        try {
            initialContext = new InitialContext();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        final EJBContext ejbContext;
        try {
            ejbContext = (SessionContext) initialContext.lookup("java:comp/EJBContext");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

        final Principal callerPrincipal = ejbContext.getCallerPrincipal();
        if (expectedPrincipal != null) {
            if (!expectedPrincipal.equals(callerPrincipal)) {
                throw new IllegalStateException("the caller principal " + callerPrincipal + " is not the expected " + expectedPrincipal);
            }
        } else {
            if (callerPrincipal != null) {
                throw new IllegalStateException("the caller principal " + callerPrincipal + " is not the expected " + expectedPrincipal);
            }
        }
    }

}
