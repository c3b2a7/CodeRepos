package me.lolico.learning.designpattern.observer;

import org.junit.Before;
import org.junit.Test;

public class DatatableSubjectTest {

    Subject subject;
    UserTableObserver observer;

    @Before
    public void init() {
        subject = new DatatableSubject();
        observer = new UserTableObserver(subject);
    }

    @Test
    public void registerObserver() {
        subject.registerObserver(observer); /*observer.register();*/
    }

    @Test
    public void removeObserver() {
        subject.removeObserver(observer); /*observer.unregister();*/
    }

    @Test
    public void notifyObserver() {
        registerObserver();
        subject.notifyObserver();
    }

    @Test
    public void testNotifyObserver() {
        registerObserver();
        subject.notifyObserver("test");
    }
}
