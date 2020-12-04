package frame.template.common;

import lombok.Data;

import java.io.InputStream;

@Data
public class ExportRes {
    private String contentType;
    private String charset;
    private String fileName;
    private InputStream inputStream;

}
