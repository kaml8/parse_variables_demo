package example;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class ParseTest {
    private static JavaClass javaClass;
    private static JavaMethod javaMethod;

    private static List<JavaClass> expected_list = new ArrayList<>();
    private static List<JavaClass> expected_list_with_primitive = new ArrayList<>();

    @BeforeClass
    public static void getJavaClass() throws IOException {
        JavaProjectBuilder builder = new JavaProjectBuilder();
        builder.addSource(new File("src/main/java/example/service/RegisterService.java"));
        javaClass = builder.getClassByName("example.service.RegisterService");
        javaMethod = javaClass.getMethods().get(0); // get Method [example.service.RegisterService.register]

        builder.addSource(new File("src/main/java/example/dao/UserDao.java"));
        builder.addSource(new File("src/main/java/example/dao/impl/UserDaoImpl.java"));
        expected_list.add(builder.getClassByName("example.dao.UserDao"));
        expected_list.add(builder.getClassByName("example.dao.impl.UserDaoImpl"));

        expected_list_with_primitive.add(builder.getClassByName("example.dao.UserDao"));
        expected_list_with_primitive.add(builder.getClassByName("example.dao.impl.UserDaoImpl"));
        expected_list_with_primitive.add(builder.getClassByName("boolean"));
    }

    @Test
    public void show_method_source_code() {
        System.out.println(javaMethod.getSourceCode());
    }

    @Test
    public void get_result_by_class_fields() {
        List<JavaClass> class_fields = javaClass.getFields()
                .stream()
                .map(JavaField::getType)
                .collect(Collectors.toList());
        assertEquals(expected_list, class_fields);
    }

    @Test
    public void get_result_by_method_arguments() {
        List<JavaClass> method_arguments = javaMethod.getParameters()
                .stream()
                .map(JavaParameter::getJavaClass)
                .collect(Collectors.toList());
        assertEquals(expected_list, method_arguments);
    }

    @Test
    public void get_result_by_method_returns() {
        assertEquals(expected_list, Arrays.asList(javaMethod.getReturns()));
    }

    @Test
    public void get_result_by_class_imports() {
        List<String> list = expected_list.stream().
                map(JavaClass::getFullyQualifiedName)
                .collect(Collectors.toList());
        assertEquals(list, javaClass.getSource().getImports());
    }

    @Test
    public void show_method_local_variables() {
        // API waiting for added, return a list containing variables in method body.
        List<JavaClass> method_local_variables = javaMethod.getLocalVariables();

        // API waiting for added, return a list without primitive type variables.
        List<JavaClass> method_reference_local_variables = javaMethod.getReferenceLocalVariables();

        assertEquals(expected_list_with_primitive, method_local_variables);
        assertEquals(expected_list, method_reference_local_variables);
    }

}

