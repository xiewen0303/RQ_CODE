package com.testPool;

import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool.Config;

/**
 * 对象池测试
 * @author  作者：wind
 * @version 创建时间：2017-7-21 下午4:15:59
 */
	
	public class ConnectionPoolFactory {  
		
		
		public static void main(String[] args) {
			Config config = new Config();  
	        config.maxActive = 16;
	        config.maxWait = 30000;  
	        ConnectionPoolFactory poolFactory = new ConnectionPoolFactory(config, "127.0.0.1", 8011);  
	        Socket socket = null ;  
	        try{
	            socket = poolFactory.getConnection();
	        }catch(Exception e){
	            e.printStackTrace();
	        }finally{
	            if(socket != null){
	                poolFactory.releaseConnection(socket);
	            }
	        }
		}
		  
	    private GenericObjectPool pool;  
	  
	    public ConnectionPoolFactory(Config config,String ip,int port){  
	        ConnectionFactory factory = new ConnectionFactory(ip, port);  
	        pool = new GenericObjectPool(factory, config);  
	    }  
	      
	    public Socket getConnection() throws Exception{  
	        return (Socket)pool.borrowObject();  
	    }  
	      
	    public void releaseConnection(Socket socket){  
	        try{  
	            pool.returnObject(socket);  
	        }catch(Exception e){  
	            if(socket != null){  
	                try{  
	                    socket.close();  
	                }catch(Exception ex){  
	                    //  
	                }  
	            }  
	        }  
	    }  
	      
	    /** 
	     * inner  
	     * @author qing 
	     * 
	     */  
	    class ConnectionFactory extends BasePoolableObjectFactory {  
	  
	        private InetSocketAddress address;  
	          
	        public ConnectionFactory(String ip,int port){  
	            address = new InetSocketAddress(ip, port);  
	        }  
	          
	        @Override  
	        public Object makeObject() throws Exception {
	            Socket socket = new Socket();  
	            socket.connect(address);  
	            return socket;  
	        }  
	          
	        public void destroyObject(Object obj) throws Exception  {  
	            if(obj instanceof Socket){  
	                ((Socket)obj).close();  
	            }  
	        }  
	  
	        public boolean validateObject(Object obj) {  
	            if(obj instanceof Socket){  
	                Socket socket = ((Socket)obj);  
	                if(!socket.isConnected()){  
	                    return false;  
	                }  
	                if(socket.isClosed()){  
	                    return false;  
	                }  
	                return true;  
	            }  
	            return false;  
	        }  
	  
	  
	    }  
	      
	}  
