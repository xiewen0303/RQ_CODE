package com.testPool;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool.Config;

import sun.awt.windows.ThemeReader;

/**
 * 任务对象池
 * @author  作者：wind
 * @version 创建时间：2017-7-21 下午4:36:16
 */
public class TestTaskPool{

	public static void main(String[] args) {
		Config config = new Config();  
        config.maxActive = 5;
        config.maxWait = 30000;  
        
        final TaskPoolFactory poolFactory = new TaskPoolFactory(config,new Param());
        
        try{
        	
        	for (int i = 0; i < 10; i++) {
        		final Task task = poolFactory.getTask();
                 task.handler();
                 
                 new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							Thread.sleep(1000);
							poolFactory.releaseTask(task);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}).start();
                 
			}
        }catch(Exception e){
            e.printStackTrace();
        }
	}
}









class TaskPoolFactory {
	
	private GenericObjectPool pool;

	public TaskPoolFactory(Config config,Param param) {
		super();
		TaskFactory taskFactory = new TaskFactory(param);
		pool = new GenericObjectPool(taskFactory,config);
	}
	
	 public Task getTask() throws Exception{  
	        return (Task)pool.borrowObject();  
	 }
	
	/**
	 * 释放对象
	 * @param task
	 */
	public void releaseTask(Task task){  
        try{  
            pool.returnObject(task);  
        }catch(Exception e){
        	e.printStackTrace();
        }
    }
}

class Task{
	public void handler(){
		System.out.println("create task id:"+this);
	}
}


class Param {
	private int p1;
	private int p2;
	
	public Param() {
	}
	
	public Param(int p1, int p2) {
		super();
		this.p1 = p1;
		this.p2 = p2;
	}

	public int getP1() {
		return p1;
	}
	public void setP1(int p1) {
		this.p1 = p1;
	}
	public int getP2() {
		return p2;
	}
	public void setP2(int p2) {
		this.p2 = p2;
	}
}

class TaskFactory extends BasePoolableObjectFactory {
	
	Param param;
	public TaskFactory(Param param) {
		this.param = param;
	}
	
	@Override
	public Object makeObject() throws Exception {
		return new Task();
	}
	
	@Override
	public void destroyObject(Object obj) throws Exception  {  
       System.out.println("destroyObject ==== ");
    }
	
	@Override
    public boolean validateObject(Object obj) {
		System.out.println("validateObject ==== ");
        return false;  
    }  
}
