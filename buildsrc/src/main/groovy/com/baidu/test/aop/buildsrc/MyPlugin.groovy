package  com.baidu.test.aop.buildsrc

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.gradle.AppExtension


public class MyPlugin implements Plugin<Project> {

    void apply(Project project) {
        System.out.println("========================");
        System.out.println("hello gradle plugin!");
        System.out.println("========================");

        // test extensions
        project.extensions.create("address",Address);
        project.extensions.create("test", TestExtenstion)

        project.task("readExtension") << {
            def address = project['address']
            println('address '+ address.province+",address.city");
            println('test name '+ project['test'].name)
        }

        def android = project.extensions.findByType(AppExtension)
        android.registerTransform(new MyTransform(project))


    }
}