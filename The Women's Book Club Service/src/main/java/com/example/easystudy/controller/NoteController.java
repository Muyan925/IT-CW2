package com.example.easystudy.controller;

import com.example.easystudy.dao.NoteDao;
import com.example.easystudy.dao.NtopDao;
import com.example.easystudy.dao.TypeDao;
import com.example.easystudy.pojo.Page;
import com.example.easystudy.result.Result;
import com.example.easystudy.pojo.Note;
import com.example.easystudy.service.NoteService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * 笔记控制器类
 * @author BB4U/WANGYU/SMH
 */
@RestController
@CrossOrigin
public class NoteController {

    //Spring推荐使用setter方式注入，所以Field注入方式不再使用
    //BB4U
    //02.16

    private NoteDao noteDao;

    private NtopDao ntopDao;

    private TypeDao typeDao;

    private NoteService noteService;

    @Autowired
    public void setNoteDao(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Autowired
    public void setNtopDao(NtopDao ntopDao) {
        this.ntopDao = ntopDao;
    }

    @Autowired
    public void setTypeDao(TypeDao typeDao) {
        this.typeDao = typeDao;
    }

    @Autowired
    public void setNoteService(NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * 获取所有笔记
     * 测试通过
     * @param uId
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/Note/GetAllNoteByUid/{uId}/{current}/{size}")
    public Result GetAllNoteByUid(@PathVariable("uId") int uId, @PathVariable("current") int current, @PathVariable("size") int size)
    {
        List<Note> NoteList = noteDao.GetAllNoteByUid(uId, (current - 1) * size, size);
        List<Note> nullNoteList = noteDao.SetDefaultTid(uId, (current - 1) * size, size);
        int total1 = noteDao.GetNoteCountByUid(uId);
        int total2 = noteDao.SetDefaultTidCount(uId);
        int pages1 = (int) Math.ceil(total1 * 1.0 / size);
        int pages2 = (int) Math.ceil(total2 * 1.0 / size);
        if (current > pages1 + pages2) {
            NoteList = noteDao.GetAllNoteByUid(uId, pages1, size);
            nullNoteList = noteDao.SetDefaultTid(uId, pages2, size);
        }
        for (Note nullNote:nullNoteList)
        {
            nullNote.setTypeName("默认类型");
        }
        NoteList.addAll(nullNoteList);
        Page page = new Page();
        page.setTotal(total1 + total2);
        page.setRecords(NoteList);
        Result result = new Result();
        result.setMsg("查询成功！");
        result.setFlag(true);
        result.setData(page);
        return result;
    }

    /**
     * 搜索笔记
     * 测试通过
     * @param uId
     * @param current
     * @param size
     * @param keyword
     * @return
     */
    @GetMapping("/Note/SelectNotesByKeyword/{uid}/{current}/{size}")
    public Result SelectNotesByKeyword(@PathVariable("uid") int uId, @PathVariable("current") int current, @PathVariable("size") int size, @RequestParam("keyword") String keyword)
    {
        Result result = new Result();
        List<Note> notes = noteDao.SelectNotesByKeyword(keyword,uId,(current - 1) * size, size);
        List<Note> nullNotes = noteDao.GetAllNullNoteByKeyword(keyword,uId,(current - 1) * size, size);
        int total1 = noteDao.SelectNoteCountByKeyword(keyword, uId);
        int total2 = noteDao.GetAllNullNoteCountByKeyword(keyword, uId);

        for (Note nullNote : nullNotes)
        {
            nullNote.setTypeName("默认类型");
        }

        notes.addAll(nullNotes);
        Page page = new Page();
        page.setTotal(total1 + total2);
        page.setRecords(notes);
        result.setData(page);
        result.setMsg("查询成功！");
        result.setFlag(true);
        return  result;
    }

    /**
     * 笔记添加
     * 测试通过
     * @param note
     * @param noteFile
     * @return
     */
    @PostMapping("/Note/AddNotes")
    public Result AddNotes(@RequestParam("note") String note, @RequestParam("noteFile") MultipartFile noteFile)
    {
        Result result = new Result();
        //将前端传回的字符串转换为json数据
        Gson gson = new Gson();
        Note note1 = gson.fromJson(note,Note.class);
        if (noteFile.isEmpty())
        {
            result.setFlag(false);
            result.setMsg("文件不能为空！");
        }
        System.out.println(note1);
        String FilePath = System.getProperty("user.dir") + "/note/";
        System.out.println(FilePath);
        String FileName = UUID.randomUUID() + ".md";
        String newFileName = FilePath + FileName;
        String FileLocation = "/note/" + FileName;
        System.out.println(FileLocation);
        File file = new File(newFileName);
        if (!file.getParentFile().exists())
        {
            file.getParentFile().mkdirs();
        }
        try {
            noteFile.transferTo(file);
            note1.setFileLocation(FileLocation);
            //SQL语句中暂时未对文件类型进行设置。
            //文件地址暂时存储为绝对路径
            noteDao.AddNotes(note1);
            result.setFlag(true);
            result.setMsg("文件创建成功！");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 显示笔记基础信息
     * 测试通过
     * @param nId
     * @return
     */
    @GetMapping("/Note/ShowNoteInfoByNid/{nId}")
    public Result ShowNoteInfoByNid(@PathVariable("nId") int nId)
    {
        Result result = new Result();
        Note note = noteDao.ShowNoteInfoByNid(nId);
        if (note==null)
        {
            Note rNote = noteDao.SetDefaultByNid(nId);
            rNote.setTypeName("default type");
            result.setData(rNote);
        }else {
            result.setData(note);
        }
        result.setMsg("successfully！");
        result.setFlag(true);
        return result;
    }

    /**
     * 删除单个笔记
     * 测试通过
     * @param nId
     * @return
     */
    @PostMapping("/Note/DeleteSingleNoteByNid/{nId}")
    public Result DeleteSingleNoteByNid(@PathVariable("nId") int nId)
    {
        Result result = new Result();
        int nNumber = noteDao.DeleteSingleNoteByNid(nId);
        int TNumber = ntopDao.deleteNtopByNid(nId);
        if (nNumber==1 && TNumber>=0)
        {
            result.setFlag(true);
            result.setMsg("successfully！！");
        }
        else
        {
            result.setFlag(false);
            result.setMsg("unsuccessfully！");
        }
        return result;
    }

    /**
     * 新增笔记到到计划
     * 测试通过
     * @param pId
     * @param nIds
     * @return
     */
    @PostMapping("/Note/AddNotesToPlan")
    public Result AddNotesToPlan(@RequestParam("pId") int pId,@RequestParam("nIds") List<String> nIds)
    {
        int number ;
        for (String nId:nIds)
        {
         ntopDao.AddNotesToPlan(Integer.parseInt(nId),pId);
        }
        //还未设置具体的判断添加成功判断逻辑
        Result result = new Result();
        result.setFlag(true);
        result.setMsg("successfully！");
        return result;
    }

    /**
     * 笔记基础信息更新
     * 测试通过
     * @param note
     * @return
     */
    @PostMapping("/Note/UpdateNoteInfoByNid")
    public Result UpdateNoteInfoByNid(@RequestBody Note note)
    {
        Result result = new Result();
        int number = noteDao.UpdateNoteInfoByNid(note);
        System.out.println(number+" " +note);
        if (number==1)
        {
            result.setFlag(true);
            result.setMsg("successfully！");
        }
        else {
            result.setFlag(false);
            result.setMsg("unsuccessfully！");
        }
        return result;
    }

    /**
     * 筛选笔记
     * 测试通过
     * @param note
     * @param current
     * @param size
     * @return
     */
    @PostMapping("/Note/FilterNotes/{current}/{size}")
    public Result FilterNotes(@RequestBody Note note, @PathVariable("current") int current, @PathVariable("size") int size)
    {
        Result result = new Result();
        List<Note> notes = noteDao.FilterNotes(note.getuId(), note.getNotePress(), note.getNoteStatus(),note.getTypeName(), (current - 1) * size, size);
        List<Note> nullNotes =  noteDao.FilterNullNotes(note.getuId(), note.getNotePress(), note.getNoteStatus(), note.getTypeName(),(current - 1) * size, size);
        int total1 = noteDao.FilterNoteCount(note.getuId(), note.getNotePress(), note.getNoteStatus(), note.getTypeName());
        int total2 = noteDao.FilterNullNoteCount(note.getuId(), note.getNotePress(), note.getNoteStatus(), note.getTypeName());

        for (Note nullNote : nullNotes)
        {
            nullNote.setTypeName("default tyoe");
        }
        notes.addAll(nullNotes);
        Page page = new Page();
        page.setRecords(notes);
        page.setTotal(total1 + total2);
        result.setData(page);
        result.setFlag(true);
        result.setMsg("successfully！");
        return  result;
    }

    /**
     * 批量删除笔记
     * 测试通过
     * @param nIds
     * @return
     */
    @PostMapping("/Note/DeleteNotesByNids")
    public Result DeleteNotesByNids(@RequestParam("nIds") List<String> nIds)
    {
        for (String nId:nIds) {
            noteDao.DeleteSingleNoteByNid(Integer.parseInt(nId));
            ntopDao.deleteNtopByNid(Integer.parseInt(nId));
        }
        //不确定判断删除成功的逻辑，暂时直接返回删除成功！
        Result result = new Result();
        result.setMsg("successfully！");
        result.setFlag(true);
        return  result;
    }

    /**
     * 获取可以添加的笔记
     * 测试通过
     * @param pId
     * @return
     */
    @GetMapping("/Note/GetNotesToAdd/{pId}")
    public Result GetNotesToAdd(@PathVariable("pId") int pId)
    {
        Result result = new Result();
        result.setMsg("successfully！");
        List<Note> notes = noteDao.GetNotesToAdd(pId);
        List<Note> nullNotes = noteDao.GetNullNotesToAdd(pId);
        for (Note nullNote : nullNotes)
        {
            nullNote.setTypeName("default type");
        }
        notes.addAll(nullNotes);
        if (notes.size()==0)
        {
            result.setFlag(false);
            result.setMsg("can not add");
        }else {
            result.setFlag(true);
            result.setMsg("successfully！");
            result.setData(notes);
        }
        return result;
    }

    /**
     * 获取笔记文件内容
     * 测试通过
     * @param fileLocation
     * @return
     * @throws IOException
     */
    @GetMapping("/Note/GetNoteInfo")
    public Result GetNoteInfo(@RequestParam("fileLocation") String fileLocation) throws IOException {
        String classpath = System.getProperty("user.dir") + fileLocation;
        System.out.println(classpath);
        File file = new File(classpath);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[1024];
        StringBuilder stringBuilder = new StringBuilder();
        while ((fileInputStream.read(bytes)) != -1)
        {
            stringBuilder.append(new String(bytes));
            bytes = new byte[1024];
        }
        fileInputStream.close();
        Result result = new Result();
        result.setData(stringBuilder.toString().trim());
        result.setFlag(true);
        result.setMsg("successfully！");
        return result;
    }

    /**
     * 笔记的在线保存
     * 测试通过
     * @param noteName
     * @param note
     * @return
     */
    @PostMapping("/notes/{name}")
    public Result saveNote(@PathVariable("name") String noteName, @RequestBody String note) {
        return noteService.saveNote(noteName, note);
    }

    /**
     * 笔记中插入图片
     * 测试通过
     * @param image
     * @return
     */
    @PostMapping("/notes/upload")
    public Result uploadImageInNote(@RequestParam("image") MultipartFile image) {
        return noteService.uploadImage(image);
    }
}
