package frame.template.facade;

import frame.template.service.QueryBusinessDataEchoConfigService;
import frame.template.vo.reqres.QueryBusinessDataEchoConfigListReq;
import frame.template.vo.reqres.QueryBusinessDataEchoConfigListRes;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QueryBusinessDataEchoConfigFacade {
    @Resource
    private QueryBusinessDataEchoConfigService queryBusinessDataEchoConfigService;

    public List<QueryBusinessDataEchoConfigListRes> queryBusinessDataEchoConfigList(
            QueryBusinessDataEchoConfigListReq req) {
        List<QueryBusinessDataEchoConfigListRes> res =
                queryBusinessDataEchoConfigService.queryBusinessDataEchoConfigList(req);
        return res;
    }
}
