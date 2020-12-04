package frame.template.vo;

import lombok.Data;

@Data
public class BusinessDataEchoConfig {
    private String id;

    private String templatePrimaryKey;

    private String objectAttributeName;

    private String objectType;

    private String querySql;

}
