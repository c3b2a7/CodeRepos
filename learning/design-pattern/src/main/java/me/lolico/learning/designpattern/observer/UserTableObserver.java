package me.lolico.learning.designpattern.observer;

/**
 * 用户数据表观察者。订阅数据表主题，收到通知后将会进行同步数据表操作。
 *
 * @author lolico
 * @see Subject
 * @see DatatableSubject
 */
public class UserTableObserver implements Observer {

    private final Subject subject;

    /**
     * @param subject 主题
     */
    public UserTableObserver(Subject subject) {
        this.subject = subject;
    }

    @Override
    public void update(Subject subject) {
        update(subject, null);
    }

    /**
     * 同步数据表操作
     *
     * @param subject 发布通知的主题
     * @param args    附加参数
     */
    @Override
    public void update(Subject subject, Object args) {
        //sync table
        System.out.println((args == null ? "" : args) + " 同步成功!");
    }

    /**
     * 向主题中注册自己
     */
    public void register() {
        subject.registerObserver(this);
    }

    /**
     * 主题中删除自己
     */
    public void unregister() {
        subject.removeObserver(this);
    }
}
