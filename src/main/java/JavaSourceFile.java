import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class JavaSourceFile implements JavaFileObject {

    private final File file;
    private final Kind kind;

    /**
     * Construct a SimpleJavaFileObject of the given kind and with the given URI.
     *
     * @param path the source path
     */
    protected JavaSourceFile( String path )
    {
        this.file =  new File( path );
        if ( !file.exists() ) {
            throw new IllegalArgumentException( "Does not exist: [" + path + "]." );
        }
        this.kind = JavaFileObject.Kind.SOURCE;
    }

    @Override
    public Kind getKind()
    {
        return this.kind;
    }

    @Override
    public boolean isNameCompatible( String simpleName, Kind kind )
    {
        String baseName = simpleName + kind.extension;

        return kind == this.kind && file.getName().equals( baseName );
    }

    @Override
    public NestingKind getNestingKind()
    {
        return null;
    }

    @Override
    public Modifier getAccessLevel()
    {
        return null;
    }

    @Override
    public URI toUri()
    {
        return this.file.toURI();
    }

    @Override
    public String getName()
    {
        String name = this.file.getName();
        System.out.println( "[INFO] name = [" + name + "]." );
        return name;
    }

    @Override
    public InputStream openInputStream() throws IOException
    {
        return new FileInputStream( this.file );
    }

    @Override
    public OutputStream openOutputStream() throws IOException
    {
        throw new UnsupportedOperationException( "not yet implemented: [JavaSourceFile::openOutputStream]." );
    }

    @Override
    public Reader openReader( boolean ignoreEncodingErrors ) throws IOException
    {
        return new BufferedReader( new FileReader( this.file ) );
    }

    @Override
    public CharSequence getCharContent( boolean ignoreEncodingErrors ) throws IOException
    {
        try (var is = openInputStream()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    @Override
    public Writer openWriter() throws IOException
    {
        throw new UnsupportedOperationException( "not yet implemented: [JavaSourceFile::openWriter]." );
    }

    @Override
    public long getLastModified()
    {
        return this.file.lastModified();
    }

    @Override
    public boolean delete()
    {
        throw new UnsupportedOperationException( "not yet implemented: [JavaSourceFile::delete]." );
    }
}
