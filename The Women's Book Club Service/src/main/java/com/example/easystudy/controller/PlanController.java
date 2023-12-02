package com.example.easystudy.controller;

import com.example.easystudy.dao.NoteDao;
import com.example.easystudy.dao.NtopDao;
import com.example.easystudy.dao.PlanDao;
import com.example.easystudy.dao.UserDao;
import com.example.easystudy.pojo.Page;
import com.example.easystudy.result.Result;
import com.example.easystudy.pojo.Note;
import com.example.easystudy.pojo.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 计划控制器类
 * @author BB4U/WNAGYU/SMH
 */
@RestController
@CrossOrigin
public class PlanController {

    //Spring推荐使用setter方式注入，所以Field注入方式不再使用
    //BB4U
    //02.16

    private UserDao userDao;

    private PlanDao planDao;

    private NoteDao noteDao;

    private NtopDao ntopDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setPlanDao(PlanDao planDao) {
        this.planDao = planDao;
    }

    @Autowired
    public void setNoteDao(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Autowired
    public void setNtopDao(NtopDao ntopDao) {
        this.ntopDao = ntopDao;
    }

    /**
     * 获取学习进度数据
     * 测试通过
     * @param uId
     * @return
     */
    @GetMapping("/plan/GetUserStudyProcessByUid/{uId}")
    public Result GetUserStudyProcessByUid(@PathVariable("uId") int uId)
    {
        Map<String,Integer> map = new HashMap<>();
        Result result = new Result();

        int completedPlansNum = planDao.selectFNumber(uId);
        int unfinishedPlansNum = planDao.selectUNumber(uId);
        int reviewedNotesNum = noteDao.selectRNumber(uId);
        int unreviewedNotesNum = noteDao.selectWNumber(uId);

        //按照文档规定需要修改了一下返回值参数名称
        //因为文档规定return的值的名称了，所以修改一下，怕师神获取不了
        //Modify by：薛永淇
        //2023.1.31

        //完成的计划，状态为1
        map.put("completedPlansNum",completedPlansNum);
        //未完成的计划，状态为0
        map.put("unfinishedPlansNum",unfinishedPlansNum);
        //已经复习的笔记，状态为1
        map.put("reviewedNotesNum",reviewedNotesNum);
        //未复习的笔记，状态为0
        map.put("unreviewedNotesNum",unreviewedNotesNum);

        result.setFlag(true);
        result.setMsg("successful");
        result.setData(map);

        return  result;
    }

    /**
     * 获取计划
     * 测试通过
     * @param uId
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/Plan/GetAllPlansByUid/{uId}/{current}/{size}")
    public Result GetAllPlansByUid(@PathVariable("uId") int uId, @PathVariable("current") int current, @PathVariable("size") int size)
    {
        Result result = new Result();
        Page page = new Page();
        List<Plan> plans = planDao.GetAllPlansByUid(uId, (current - 1) * size, size);
        int total = planDao.GetPlanCountByUid(uId);
        int pages = (int) Math.ceil(total * 1.0 / size);
        if (current > pages) {
            plans = planDao.GetAllPlansByUid(uId, pages, size);
        }
        if(plans!=null)
        {
            page.setTotal(total);
            page.setRecords(plans);
            result.setFlag(true);
            result.setMsg("successful!");
            result.setData(page);
        }
        else
        {
            result.setFlag(false);
            result.setMsg("unsuccessful！");
        }
        return result;
    }

    /**
     * 搜索计划
     * 测试通过
     * @param uId
     * @param keyword
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/Plan/SelectPlansByKeyword/{uid}/{current}/{size}")
    public Result SelectPlansByKeyword(@PathVariable("uid") int uId, @RequestParam("keyword") String keyword, @PathVariable("current") int current, @PathVariable("size") int size)
    {

        Result result = new Result();
        Page page = new Page();
        List<Plan> plans = planDao.SelectPlansByKeyword(uId,keyword,(current - 1) * size, size);
        int total = planDao.SelectPlanCountByKeyword(uId, keyword);
        page.setRecords(plans);
        page.setTotal(total);
        result.setData(page);
        result.setMsg("successful！");
        result.setFlag(true);
        return result;
    }

    /**
     * 增加计划
     * 测试通过
     * @param plan
     * @return
     */
    @PostMapping("/Plan/AddPlans")
    public Result AddPlans(@RequestBody Plan plan)
    {
        int number = planDao.AddPlans(plan);
        Result result = new Result();
        if (number==1)
        {
            result.setFlag(true);
            result.setMsg("successfully！");
        }else
        {
            result.setFlag(false);
            result.setMsg(" unsuccessfully！");
        }
        return result;
    }

    /**
     * 计划单个删除
     * 测试通过
     * @param pId
     * @return
     */
    @PostMapping("/Plan/DeleteSinglePlanByPid/{pId}")
    public Result DeleteSinglePlanByPid(@PathVariable("pId") int pId)
    {
        int nNumber = ntopDao.deleteNtopByPid(pId);
        int pNumber = planDao.DeleteSinglePlanByPid(pId);
        Result result = new Result();
        if (nNumber>=0 && pNumber==1)
        {
            result.setFlag(true);
            result.setMsg("successfully！");
        }else
        {
            result.setFlag(false);
            result.setMsg(" unsuccessfully！");
        }
        return result;
    }

    /**
     * 计划批量删除
     * 测试通过
     * @param pIds
     * @return
     */
    @PostMapping("/Plan/DeletePlansByPids")
    public Result DeletePlansByPids(@RequestParam("pIds") List<String> pIds)
    {
        for (String pId:pIds) {
            ntopDao.deleteNtopByPid(Integer.parseInt(pId));
            planDao.DeleteSinglePlanByPid(Integer.parseInt(pId));
        }
        //不确定判断成功删除逻辑暂时直接定位成功返回
        Result result = new Result();
        result.setFlag(true);
        result.setMsg("successfully！");
        return result;
    }

    /**
     * 显示计划基本信息
     * 测试通过
     * @param pId
     * @return
     */
    @GetMapping("Plan/ShowPlanBasicInfoByPid/{pId}")
    public Result ShowPlanBasicInfoByPid(@PathVariable("pId") int pId)
    {
        Plan plan = planDao.ShowPlanBasicInfoByPid(pId);
        Result result = new Result();
        result.setFlag(true);
        result.setMsg("successfully！");
        result.setData(plan);
        return result;
    }

    /**
     * 修改计划基础信息
     * 测试通过
     * @param plan
     * @return
     */
    @PostMapping("/Plan/UpdatePlanBasicInfo")
    public Result UpdatePlanBasicInfo(@RequestBody Plan plan)
    {
        int number = planDao.UpdatePlanBasicInfo(plan);
        Result result = new Result();
        if (number==1)
        {
            result.setFlag(true);
            result.setMsg(" successfully");
        }else
        {
            result.setFlag(false);
            result.setMsg("unsuccessfully");
        }
        return result;
    }

    /**
     * 从计划中移除笔记
     * 测试通过
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/Plan/RemoveNotesFromPlan")
    public Result RemoveNotesFromPlan(HttpServletRequest httpServletRequest)
    {
        Integer pId = Integer.valueOf(httpServletRequest.getParameter("pId"));
        Integer nId = Integer.valueOf(httpServletRequest.getParameter("nId"));
        int number = ntopDao.RemoveNotesFromPlan(pId,nId);
        Result result = new Result();
        if (number==1)
        {
            result.setFlag(true);
            result.setMsg("successfully");
        }
        else {
            result.setFlag(false);
            result.setMsg("Unsuccessfully");
        }
        return result;
    }

    /**
     * 筛选计划
     * 通过测试
     * @param plan
     * @param current
     * @param size
     * @return
     */
    @PostMapping("/Plan/FilterPlans/{current}/{size}")
    public Result FilterPlans(@RequestBody Plan plan, @PathVariable("current") int current, @PathVariable("size") int size)
    {
        Result result = new Result();
        Page page = new Page();
        page.setBeginPage(0);
        List<Plan> plans = planDao.FilterPlans(plan.getPlanStress(), plan.getPlanStatus(), plan.getuId(), (current - 1) * size, size);
        int total = planDao.FilterPlanCount(plan);
        page.setTotal(total);
        page.setRecords(plans);
        result.setData(page);
        result.setFlag(true);
        result.setMsg("successfully！");
        return result;
    }

    /**
     * 获取计划中的笔记
     * 通过测试
     * @param pId
     * @return
     */
    @GetMapping("/Plan/ShowPlanNotesInfoByPid/{pId}")
    public Result ShowPlanNotesInfoByPid(@PathVariable("pId") int pId)
    {
        List<Integer> nIds = ntopDao.ShowNidByPid(pId);
        List<Note> noteList = new ArrayList<>();
        Result result = new Result();
        for (Integer nId:nIds) {
            Note note = noteDao.ShowNoteInfoByNid(nId);
            if (note!=null) {
                noteList.add(note);
            }
        }
        for (Integer nId:nIds) {
            Note note = noteDao.SetDefaultByNid(nId);
            if (note!=null) {
                note.setTypeName("default type");
                noteList.add(note);
            }
        }
        result.setFlag(true);
        result.setMsg("successfully");
        result.setData(noteList);
        return result;
    }
}
