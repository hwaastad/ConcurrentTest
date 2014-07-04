/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.concurrenttest;

import java.security.Principal;
import java.util.concurrent.Future;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.naming.NamingException;
import static org.apache.openejb.config.EjbJarInfoBuilder.logger;

/**
 *
 * @author Helge Waastad <helge@waastad.org>
 */
@Stateless
@RunAs("tomee")
public class ExecutorBean {

    @Resource
    private ManagedExecutorService executorService;

    @Resource
    private EJBContext ejbContext;
    
    public Future<?> submit(RunnableTest task) throws NamingException {
        final Principal principal = ejbContext.getCallerPrincipal();
        System.out.println("Principal:"+ principal.getName());
        task.setExpectedPrincipal(principal);
        return executorService.submit(task);
    }
}
