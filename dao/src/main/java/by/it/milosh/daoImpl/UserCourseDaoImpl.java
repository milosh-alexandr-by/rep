package by.it.milosh.daoImpl;

import by.it.milosh.dao.UserCourseDao;
import by.it.milosh.pojos.Course;
import by.it.milosh.pojos.User;
import by.it.milosh.pojos.UserCourse;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserCourseDaoImpl extends BaseDaoImpl<UserCourse> implements UserCourseDao {
    private static Logger logger = Logger.getLogger(UserCourseDaoImpl.class);

    /**
     * Determine all courses, on which specific user subscribed.
     * User is determined by user_id.
     * @param user_id
     * @return
     */
    @Override
    public List<UserCourse> getAllUserCourseByUserId(Long user_id) {
        String hql = "from UserCourse uc where uc.user.user_id=:user_id";
        Query query = getSession().createQuery(hql);
        query.setParameter("user_id", user_id);
        List<UserCourse> userCourses = query.list();
        return userCourses;
    }

    /**
     * User is subscribing on course.
     * @param user_id
     * @param course_id
     */
    @Override
    public void addCourseToUser(Long user_id, Long course_id) {
        Session session = getSession();
        User user = (User) session.get(User.class, user_id);
        Course course = (Course) session.get(Course.class, course_id);
        UserCourse userCourse = new UserCourse();
        userCourse.setUser(user);
        userCourse.setCourse(course);
        session.saveOrUpdate(userCourse);
    }

    /**
     * Extract all students, which subscribed on specific course.
     * Course is determined by course_id.
     * @param course_id
     * @return
     */
    @Override
    public List<UserCourse> getAllUserCourseByCourseId(Long course_id) {
        String hql = "from UserCourse uc where uc.course.course_id=:course_id and uc.user.role.roleName=:roleName";
        Query query = getSession().createQuery(hql);
        query.setParameter("course_id", course_id);
        query.setParameter("roleName", "ROLE_STUDENT");
        List<UserCourse> userCourses = query.list();
        return userCourses;
    }

    /**
     * Extract all teachers, which subscribed on specific course.
     * Course is determined by course_id.
     * @param course_id
     * @return
     */
    @Override
    public List<UserCourse> checkTeacherCourse(Long course_id) {
        String hql = "from UserCourse uc where uc.course.course_id=:course_id and uc.user.role.roleName=:roleName";
        Query query = getSession().createQuery(hql);
        query.setParameter("course_id", course_id);
        query.setParameter("roleName", "ROLE_TEACHER");
        List<UserCourse> userCourses = query.list();
        return userCourses;
    }

    /**
     * Extract all UserCourse from DB.
     * @return
     */
    @Override
    public List<UserCourse> getAllUserCourse() {
        String hql = "from UserCourse";
        Query query = getSession().createQuery(hql);
        List<UserCourse> userCourses = query.list();
        return userCourses;
    }
}

