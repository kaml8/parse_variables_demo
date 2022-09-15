package example.parser;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;

import java.io.File;
import java.io.IOException;

/* Method [example.service.RegisterService.register]：

public static boolean register(String name, String password) {

    UserDao userDao = UserDaoImpl.getInstance();
    boolean flag = false;

    if (userDao.addUser(name, password)) {
        flag = true;
    }

    return flag;
}
*/

/**
 * I want to get such result from Method [example.service.RegisterService.register]:
 * [example.dao.UserDao, example.dao.impl.UserDaoImpl]
 *
 * Then I can do some reference relations record such as:
 * Class [example.service.RegisterService]
 * —rely on→
 * {Interface[example.dao.UserDao],Class [example.dao.impl.UserDaoImpl]}
 */
public class Test {

    public static JavaClass getJavaClass() throws IOException {
        JavaProjectBuilder builder = new JavaProjectBuilder();
        builder.addSource(new File("src/main/java/example/service/RegisterService.java"));
        return builder.getClassByName("example.service.RegisterService");
    }

    /**
     * Through `javaClass.getSource().getImports()`, I got more items than excepted:
     *
     * The last two imports showed in result are unnecessary because they have no contributions to analysis this method.
     */
    @org.junit.Test
    public void showImports() throws IOException {
        System.out.println(getJavaClass().getSource().getImports()); // [example.dao.UserDao, example.dao.impl.UserDaoImpl, java.util.*, java.io.*]
    }

    /**
     * Through `javaClass.getFields()`, I can only get class fields.
     * Through `javaMethod.getParameters()` and `javaMethod.getReturns()` , I can't get `UserDao` and `UserDaoImpl'
     *
     * I need some ways to parser these local variables in method content like UserDao` and `UserDaoImpl'.#
     *
     */
    @org.junit.Test
    public void show_parameters_and_returns() throws IOException {
        JavaClass javaClass = getJavaClass();
        System.out.println(javaClass.getFields());  // [String example.service.RegisterService.path]

        JavaMethod javaMethod = javaClass.getMethods().get(0);
        System.out.println(javaMethod.getParameters()); // [String name, String password]
        System.out.println(javaMethod.getReturns());    // boolean
    }
}

