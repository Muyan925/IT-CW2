package com.example.easystudy.dao;

import com.example.easystudy.pojo.Note;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 笔记Dao
 * @author BUB
 */
@Mapper
public interface NoteDao {
    /**
     * 获取复习笔记
     * @param uId
     * @return
     */
    int selectRNumber(@Param("uId") int uId);

    /**
     * 获取未复习笔记
     * @param uId
     * @return
     */
    int selectWNumber(@Param("uId") int uId);

    /**
     * 删除笔记
     * @param tId
     * @return
     */
    int deleteNoteByTid(@Param("tId") int tId);

    /**
     * 从计划中移除笔记
     * @return
     */
    int RemoveNotesFromPlan();

    /**
     * 获取用户所有笔记
     * @param uId
     * @param beginPage
     * @param pageSize
     * @return
     */
    List<Note> GetAllNoteByUid(@Param("uId") int uId, @Param("beginPage") int beginPage, @Param("pageSize") int pageSize);

    /**
     * 获取用户上传笔记数
     * @param uId
     * @return
     */
    int GetNoteCountByUid(@Param("uId") int uId);

    /**
     * 新增笔记
     * @param note
     * @return
     */
    int AddNotes(Note note);

    /**
     * 显示笔记内容
     * @param nId
     * @return
     */
    Note ShowNoteInfoByNid(@Param("nId") int nId);

    /**
     * 搜索笔记
     * @param noteName
     * @param uId
     * @param beginPage
     * @param pageSize
     * @return
     */
    List<Note> SelectNotesByKeyword(@Param("noteName") String noteName,@Param("uId") int uId, @Param("beginPage") int beginPage, @Param("pageSize") int pageSize);

    /**
     * 搜索计划（分页用）
     * @param noteName
     * @param uId
     * @return
     */
    int SelectNoteCountByKeyword(@Param("noteName") String noteName,@Param("uId") int uId);

    /**
     * 单个删除计划
     * @param nId
     * @return
     */
    int DeleteSingleNoteByNid(@Param("nId") int nId);

    /**
     * 筛选计划
     * @param uId
     * @param notePress
     * @param noteStatus
     * @param typeName
     * @param beginPage
     * @param pageSize
     * @return
     */
    List<Note> FilterNotes(@Param("uId") int uId, @Param("notePress") String notePress, @Param("noteStatus") int noteStatus, @Param("typeName") String typeName, @Param("beginPage") int beginPage, @Param("pageSize") int pageSize);

    /**
     * 筛选计划数（分页用）
     * @param uId
     * @param notePress
     * @param noteStatus
     * @param typeName
     * @return
     */
    int FilterNoteCount(@Param("uId") int uId, @Param("notePress") String notePress, @Param("noteStatus") int noteStatus, @Param("typeName") String typeName);

    /**
     * 更新用户笔记内容
     * @param note
     * @return
     */
    int UpdateNoteInfoByNid(Note note);

    /**
     * 获取可以添加到当前计划的笔记
     * @param pId
     * @return
     */
    List<Note> GetNotesToAdd(@Param("pId") int pId);

    /**
     * 设置笔记默认类型
     * @param uId
     * @param beginPage
     * @param pageSize
     * @return
     */
    List<Note>  SetDefaultTid(@Param("uId") int uId, @Param("beginPage") int beginPage, @Param("pageSize") int pageSize);

    /**
     * 设置默认类型（分页用）
     * @param uId
     * @return
     */
    int SetDefaultTidCount(@Param("uId") int uId);

    /**
     * 获取类型为空的笔记
     * @param nId
     * @return
     */
    Note SetDefaultByNid(@Param("nId") int nId);

    /**
     * 搜索笔记（类型为空）
     * @param noteName
     * @param uId
     * @param beginPage
     * @param pageSize
     * @return
     */
    List<Note> GetAllNullNoteByKeyword(@Param("noteName") String noteName,@Param("uId") int uId, @Param("beginPage") int beginPage, @Param("pageSize") int pageSize);

    /**
     * 搜索笔记（类型为空，分页用）
     * @param noteName
     * @param uId
     * @return
     */
    int GetAllNullNoteCountByKeyword(@Param("noteName") String noteName,@Param("uId") int uId);

    /**
     * 筛选笔记
     * @param uId
     * @param notePress
     * @param noteStatus
     * @param typeName
     * @param beginPage
     * @param pageSize
     * @return
     */
    List<Note> FilterNullNotes(@Param("uId") int uId, @Param("notePress") String notePress, @Param("noteStatus") int noteStatus, @Param("typeName") String typeName, @Param("beginPage") int beginPage, @Param("pageSize") int pageSize);

    /**
     * 筛选笔记（分页用）
     * @param uId
     * @param notePress
     * @param noteStatus
     * @param typeName
     * @return
     */
    int FilterNullNoteCount(@Param("uId") int uId, @Param("notePress") String notePress, @Param("noteStatus") int noteStatus, @Param("typeName") String typeName);

    /**
     * 筛选笔记（类型为空，分页用）
     * @param pId
     * @return
     */
    List<Note> GetNullNotesToAdd(@Param("pId") int pId);
}
