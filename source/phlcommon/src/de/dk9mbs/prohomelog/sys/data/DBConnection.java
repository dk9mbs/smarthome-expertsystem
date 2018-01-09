package de.dk9mbs.prohomelog.sys.data;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author mbuehler
 */
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;



public class DBConnection {
    private static final Logger logger=Logger.getLogger( DBConnection.class.getName() );
    
    private String _uid = "";
    private String _pwd = "";
    private String _hostname = "";
    private String _port = "";
    private String _dbname = "";
    private String _classname = "";
    private Connection _connection=null;
    
    /**
     * Get the value of getConnection
     *
     * @return the value of getConnection
     */
    public Connection getGetConnection() {
        return this._connection;
    }

    /**
     * Set the value of getConnection
     *
     * @param connection new value of getConnection
     */
    public void setGetConnection(Connection connection) {
        this._connection = connection;
    }

    public DBConnection(java.util.Properties dbparams) {
        // init the database connection
        
        this._uid=(String)dbparams.get("uid");
        this._pwd=(String)dbparams.get("pwd");
        this._hostname=(String)dbparams.get("server");
        this._port=(String)dbparams.get("port");
        this._dbname=(String)dbparams.get("database");
        this._classname="org.gjt.mm.mysql.Driver";
        this._connection=null;
    }

    @Override
    public String toString() {
        return "AppInfo{" + "_uid=" + _uid + ", _pwd=" + _pwd 
                + ", _hostname=" + _hostname + ", _port=" + _port 
                + ", _dbname=" + _dbname + ", _classname=" 
                + _classname + '}';
    }
    
   
    public boolean open() {
        // Immer neu oeffnen!!!
        //if ( this._connection != null) {
        //    return true;
        //}
        
        try {
      	    Class.forName(this._classname).newInstance();
        } 
        catch (Exception e) { 
            this._printerror(e);
            return false;
        } 
        
        String url = "jdbc:mysql://"+this._hostname+":"+this._port+"/"+this._dbname; 
        
        try {
            this._connection = DriverManager.getConnection(url, this._uid, this._pwd);
        }
        catch (SQLException sqle) { 
            this._printerror(sqle);
            return false;
        } 
    
        return true;
    }
    
    public int beginTransaction() {
        //ToDo: Begin the transaction.
        return 0;
    }
    
    public int commitTransaction() {
        try {
            this._connection.commit();
        }
        catch (SQLException sqle) {
            this._printerror(sqle);
            return sqle.getErrorCode();
        }
        
        return 0;
    }
    
    public int rollbackTransaction(Savepoint savepoint) {
        try {
            this._connection.rollback(savepoint);
        }
        catch (SQLException sqle) {
            this._printerror(sqle);
            return sqle.getErrorCode();
        }
        
        return 0;
    }
    
    public boolean execQuery(String sql) {
        try {
            Statement stm=this._connection.createStatement();
            stm.executeUpdate(sql);
        } catch(java.sql.SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        
        return true;
    }
    
    public void closeConnection() {
        try {
            this._connection.close();
        } 
        catch (SQLException sqle) {
            this._printerror(sqle);
        }
    }
    
    //in this area are private helper methods 
    private void _printerror(SQLException sqle){
         
        de.dk9mbs.prohomelog.sys.data.Msgbox.show(null
                , sqle.getMessage()
                , "DBConnection:");
        System.out.println ("SQLException:" + sqle.getMessage());
        System.out.println("SQLState: " + sqle.getSQLState()); 
        System.out.println("VendorError: " + sqle.getErrorCode()); 
        sqle.printStackTrace();  
    }
    
    private void _printerror(Exception e) {
        System.err.println("Unable to load driver."); 
        e.printStackTrace(); 
    }
    
}
