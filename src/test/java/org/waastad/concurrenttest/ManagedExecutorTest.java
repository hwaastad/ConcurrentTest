/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.concurrenttest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.apache.openejb.junit.jee.EJBContainerRule;
import org.apache.openejb.junit.jee.InjectRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 *
 * @author Helge Waastad <helge@waastad.org>
 */
public class ManagedExecutorTest {

    @ClassRule
    public static final EJBContainerRule CONTAINER_RULE = new EJBContainerRule();

    @Rule
    public final InjectRule injectRule = new InjectRule(this, CONTAINER_RULE);

    @EJB
    private ExecutorBean executorBean;

    public ManagedExecutorTest() {
    }

    @Test
    public void testSomeMethod() throws Exception {
        executorBean.submit(new RunnableTest()).get();
    }

}
