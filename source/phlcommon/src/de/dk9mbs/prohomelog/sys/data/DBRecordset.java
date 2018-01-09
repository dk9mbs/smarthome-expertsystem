package de.dk9mbs.prohomelog.sys.data;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//import de.dk9mbs.tuxlog.app.dataexchange.dataimport.XlogImport;
import java.sql.*;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
        
/**
 *
 * @author mbuehler
 */
public class DBRecordset {
    private static final Logger logger=Logger.getLogger( DBRecordset.class.getName() );
    
    private DBConnection _cnn=null;
    private String _sql="";
    private boolean _updatable=false;
    private boolean _scrollable=false;
    private ResultSet _rs=null;
    private boolean _eof=false;
    private boolean _addnew=false;
    private String _message="";
    private boolean _showErrorDialog=true;
    private ClientInfo _clientinfo=null;
    
    public DBRecordset(ClientInfo clientinfo, String sql
            , boolean updatable) {
        this._cnn= clientinfo.getGetDBConnection();
        this._sql=sql;
        this._updatable=updatable;
        this._addnew=false;
        this._clientinfo=clientinfo;
        this.open();
    }

    public void setShowErrorDialog(boolean _showErrorDialog) {
        this._showErrorDialog = _showErrorDialog;
    }

    /**
     * Get the value of getResultSet
     *
     * @return the value of getResultSet
     */
    public ResultSet getResultSet() {
        return this._rs;
    }

    
    public ResultSetMetaData getMetaData() {
        try {
            return this._rs.getMetaData();
        } catch (SQLException e ) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        
        return null;
    }
    
    public int getColumnCount() {
        try{
           return this._rs.getMetaData().getColumnCount();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return -1;
    }
    
    public String getColumnName(int index) {
        try{
           return this._rs.getMetaData().getColumnName(index);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return "";
    }
    
    
    public boolean eof() {
        return this._eof;
    }
    
    public boolean first() {
        try {
            boolean ok = this._rs.first();
            this._eof = !ok;
            return ok;
        } catch(SQLException e) {
            this._printerror(e);
            return false;
        }
    }
    
    public boolean addnew() {
        try {
            this._rs.moveToInsertRow();
            this._addnew=true;
            return true;
        } catch(SQLException e) {
            this._printerror(e);
            return false;
        }
    }
    
    public boolean next() {
        try {
            return this._rs.next();
        } catch (SQLException e) {
            this._printerror(e);
            return false;
        }
    }
    
    public boolean delete() {
        try{
            this._rs.deleteRow();
            return true;
        } catch(SQLException e) {
            this._printerror(e);
            return false;
        }
    }

    public boolean close() {
        try{
            this._rs.close();
            return true;
        } catch(SQLException e) {
            this._printerror(e);
            return false;
        }
    }
    public String getString(String colname) {
        try {        
            return Objects.toString(this._rs.getString(colname), "");
        } catch (SQLException e) {
            this._printerror(e);
            return "";
        }
    }

    public String getString(int col) {
        try {        
            return Objects.toString(this._rs.getString(col), "");
        } catch (SQLException e) {
            this._printerror(e);
            return "";
        }
    }
    
    public int getInt(String colname) {
        try {        
            return this._rs.getInt(colname);
        } catch (SQLException e) {
            this._printerror(e);
            return 0;
        }
    }

    public int getInt(int col) {
        try {        
            return this._rs.getInt(col);
        } catch (SQLException e) {
            this._printerror(e);
            return 0;
        }
    }
    
    public boolean getBoolean(String colname) {
        try {        
            boolean result=this._rs.getBoolean(colname);
            return result;
        } catch (SQLException e) {
            this._printerror(e);
            return false;
        }
    }

    public boolean getBoolean(int col) {
        try {        
            return this._rs.getBoolean(col);
        } catch (SQLException e) {
            this._printerror(e);
            return false;
        }
    }
    
    public boolean setField(String col, String value) {
        try {        
            this._rs.updateString(col, value);
            return true;
        } catch (SQLException e) {
            this._printerror(e);
            return false;
        }
    }

    public boolean setField(String col, int value) {
        try {        
            this._rs.updateInt(col, value);
            return true;
        } catch (SQLException e) {
            this._printerror(e);
            return false;
        }
    }

    public boolean setField(String col, boolean value) {
        try {        
            this._rs.updateBoolean(col, value);
            return true;
        } catch (SQLException e) {
            this._printerror(e);
            return false;
        }
    }
    
    public boolean update(){
        try {
            if (this._addnew==true){
                this._rs.insertRow();
            } else {
                this._rs.updateRow();
            }
            return true;
        } catch (SQLException e) {
            this._printerror(e);
            return false;
        }
    }
    
    public boolean open() {
        return this._open(true);
    }

    public boolean open(boolean moveFirst) {
        return this._open(moveFirst);
    }
    
    private boolean _open(boolean moveFirst) {
        try {
            int modeUpdatable;
            int modeForward;

            if (this._updatable == true) {
                modeUpdatable = ResultSet.CONCUR_UPDATABLE;
            } else {
                modeUpdatable = ResultSet.CONCUR_READ_ONLY;
            }
            
            if (this._scrollable == true) {
                modeForward = ResultSet.TYPE_SCROLL_SENSITIVE;
            } else {
                modeForward = ResultSet.TYPE_FORWARD_ONLY ;
            }
            
            Statement statement = this._cnn.getGetConnection().createStatement(modeForward,
                   modeUpdatable);
            
            ResultSet rs = statement.executeQuery(this._sql);
            this._rs = rs;
            // jump to the first record.
            // If there is no recordcord then set eof=true!
            if(moveFirst==true) {
                this._eof = ! this._rs.next();
            }
            return true;
    }
        catch (SQLException sqle) {
            this._printerror(sqle);
            return false;
        }

    }

    public String getMessage() {
        return this._message;
    }
    
    // TODO: create a helper class for printerr!
    private void _printerror(SQLException sqle){

        
        logger.log(Level.SEVERE, sqle.getMessage(), sqle);
        if ( this._showErrorDialog ) {
            de.dk9mbs.prohomelog.sys.data.Msgbox.show(null
                    , sqle.getMessage()
                    , "DBRecordset:");
        }

        this._message=sqle.getMessage();
    }
    
}
