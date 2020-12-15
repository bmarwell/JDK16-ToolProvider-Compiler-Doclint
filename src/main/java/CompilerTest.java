import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

public class CompilerTest {

    private static JavaCompiler COMPILER = ToolProvider.getSystemJavaCompiler();

    public static void main(String[] args) {
        List<JavaSourceFile> javaSourceFiles = Arrays.stream( args )
                .map( JavaSourceFile::new )
                .collect( Collectors.toList() );

        JavaCompiler.CompilationTask task = COMPILER.getTask(
                null,
                null,
                null,
                List.of( "-Xdoclint:-options" ),
                emptyList(),
                javaSourceFiles
        );

        task.call();
    }
}
