package me.lolico.learning.springboot.repository;

import me.lolico.learning.springboot.pojo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lolico
 */
@Repository
@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User, Integer> {

    /**
     * 根据用户名和密码查询
     *
     * @param username 用户名
     * @param password 密码
     * @return 查询结果
     */
    User findByUsernameAndPassword(String username, String password);

    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return 用户
     */
    User findByUsername(String username);

    /**
     * 根据包含模式的匹配串模糊查询姓名匹配的记录
     * username不能为null且需要包含模式匹配字符，比如"tester_"、"%tester%"
     *
     * @param username 包含模式匹配符的条件，不能为null
     * @param pageable 分页
     * @return 查询结果集
     */
    Page<User> findByUsernameLike(String username, Pageable pageable);

    /**
     * 根据id,更新emial
     *
     * @param user 实体
     * @return 影响的行数
     * @see BaseRepository#dynamicUpdate(Object)
     * @deprecated 使用 <code>{@link BaseRepository#dynamicUpdate(Object)}</code> 代替这个方法。
     */
    @Deprecated
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update User as u set u.email=:#{#user.email} where u.id =:#{#user.id}")
    int dynamicUpdateEmailById(@Param("user") User user);

    /**
     * 根据id,更新emial
     *
     * @param id    id
     * @param email 更新后的email
     * @return 影响的行数
     * @see BaseRepository#dynamicUpdate(Object)
     * @deprecated 使用 <code>{@link BaseRepository#dynamicUpdate(Object)}</code> 代替这个方法。
     */
    @Deprecated
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update User u set u.email=?2 where u.id=?1")
    int dynamicUpdateEmailById(int id, String email);

    /**
     * 根据包含模式的匹配串模糊查询姓名匹配并且email匹配的记录
     * 传入的username可以为null,需要包含模式匹配字符，比如"tester_"、"%tester%"
     *
     * @param username 包含模式匹配符的条件，可以为null
     * @param email    email
     * @return 查询结果集
     */
    @Query("select u from User u where (u.username like ?1 or ?1 is null) and u.email =?2")
    List<User> findByUsernameLikeAndEmail(String username, String email);

}

