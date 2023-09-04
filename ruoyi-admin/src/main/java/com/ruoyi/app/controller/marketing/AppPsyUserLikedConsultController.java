package com.ruoyi.app.controller.marketing;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.framework.web.service.AppTokenService;
import com.ruoyi.user.domain.PsyUserLikedConsult;
import com.ruoyi.user.service.IPsyUserLikedConsultService;
import com.ruoyi.user.vo.PsyUserLikedConsultVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的关注
 *
 * @author ruoyi
 * @date 2022-08-26
 */
@RestController
@RequestMapping("/app/user/liked/consult")
public class AppPsyUserLikedConsultController extends BaseController {

    @Resource
    private AppTokenService appTokenService;

    @Resource
    private IPsyUserLikedConsultService psyConsultFocusService;

    @PostMapping("/getLiked")
    public AjaxResult getLiked(@RequestBody PsyUserLikedConsult req) {
        return AjaxResult.success(psyConsultFocusService.getLiked(req));
    }

    @GetMapping("/getLikes")
    public TableDataInfo getLikes(HttpServletRequest request) {
        Integer id = appTokenService.getUserId(request);
        List<PsyUserLikedConsultVO> list = new ArrayList<>();
        startPage();
        if (id == -1) {
            return getDataTable(list);
        }

        list = psyConsultFocusService.getLikes(id);
        return getDataTable(list);
    }

    @PostMapping
    public AjaxResult add(@RequestBody PsyUserLikedConsult req) {
        return AjaxResult.success(psyConsultFocusService.add(req));
    }

    @PostMapping("/del")
    public AjaxResult del(@RequestBody PsyUserLikedConsult req) {
        return AjaxResult.success(psyConsultFocusService.del(req));
    }

}
