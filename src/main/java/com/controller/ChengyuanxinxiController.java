package com.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.ChengyuanxinxiEntity;
import com.entity.view.ChengyuanxinxiView;

import com.service.ChengyuanxinxiService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 成员信息
 * 后端接口
 * @author 
 * @email 
 * @date 2022-03-13 19:21:35
 */
@RestController
@RequestMapping("/chengyuanxinxi")
public class ChengyuanxinxiController {
    @Autowired
    private ChengyuanxinxiService chengyuanxinxiService;


    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ChengyuanxinxiEntity chengyuanxinxi,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date shenqingriqistart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date shenqingriqiend,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("shetuan")) {
			chengyuanxinxi.setShetuanbianhao((String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("yonghu")) {
			chengyuanxinxi.setYonghuming((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<ChengyuanxinxiEntity> ew = new EntityWrapper<ChengyuanxinxiEntity>();
                if(shenqingriqistart!=null) ew.ge("shenqingriqi", shenqingriqistart);
                if(shenqingriqiend!=null) ew.le("shenqingriqi", shenqingriqiend);
		PageUtils page = chengyuanxinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, chengyuanxinxi), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ChengyuanxinxiEntity chengyuanxinxi, 
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date shenqingriqistart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date shenqingriqiend,
		HttpServletRequest request){
        EntityWrapper<ChengyuanxinxiEntity> ew = new EntityWrapper<ChengyuanxinxiEntity>();
                if(shenqingriqistart!=null) ew.ge("shenqingriqi", shenqingriqistart);
                if(shenqingriqiend!=null) ew.le("shenqingriqi", shenqingriqiend);
		PageUtils page = chengyuanxinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, chengyuanxinxi), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ChengyuanxinxiEntity chengyuanxinxi){
       	EntityWrapper<ChengyuanxinxiEntity> ew = new EntityWrapper<ChengyuanxinxiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( chengyuanxinxi, "chengyuanxinxi")); 
        return R.ok().put("data", chengyuanxinxiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ChengyuanxinxiEntity chengyuanxinxi){
        EntityWrapper< ChengyuanxinxiEntity> ew = new EntityWrapper< ChengyuanxinxiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( chengyuanxinxi, "chengyuanxinxi")); 
		ChengyuanxinxiView chengyuanxinxiView =  chengyuanxinxiService.selectView(ew);
		return R.ok("查询成员信息成功").put("data", chengyuanxinxiView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ChengyuanxinxiEntity chengyuanxinxi = chengyuanxinxiService.selectById(id);
        return R.ok().put("data", chengyuanxinxi);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ChengyuanxinxiEntity chengyuanxinxi = chengyuanxinxiService.selectById(id);
        return R.ok().put("data", chengyuanxinxi);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ChengyuanxinxiEntity chengyuanxinxi, HttpServletRequest request){
    	chengyuanxinxi.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(chengyuanxinxi);
        chengyuanxinxiService.insert(chengyuanxinxi);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ChengyuanxinxiEntity chengyuanxinxi, HttpServletRequest request){
    	chengyuanxinxi.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(chengyuanxinxi);
        chengyuanxinxiService.insert(chengyuanxinxi);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ChengyuanxinxiEntity chengyuanxinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(chengyuanxinxi);
        chengyuanxinxiService.updateById(chengyuanxinxi);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        chengyuanxinxiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<ChengyuanxinxiEntity> wrapper = new EntityWrapper<ChengyuanxinxiEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("shetuan")) {
			wrapper.eq("shetuanbianhao", (String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("yonghu")) {
			wrapper.eq("yonghuming", (String)request.getSession().getAttribute("username"));
		}

		int count = chengyuanxinxiService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	







}
