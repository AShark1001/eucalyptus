import com.eucalyptus.auth.*;
import com.eucalyptus.auth.principal.*;
import com.eucalyptus.entities.EntityWrapper;
import com.eucalyptus.records.BaseRecord;
import groovy.sql.Sql;

EntityWrapper db = EntityWrapper.get( BaseRecord.class );
Sql sql = new Sql( db.getSession( ).connection( ) )
Users.listAllUsers().each{ User user ->
  def u = new UserReportInfo() {{
          userName = user.getName() 
        }
      };
  sql.rows("SELECT UNIX_TIMESTAMP(record_timestamp)*1000 as timestamp, record_extra as details FROM eucalyptus_records.records_logs WHERE record_class LIKE 'VM' AND record_extra LIKE 'user=admin:%:state=RUNNING:%' AND UNIX_TIMESTAMP(record_timestamp)*1000 < ${notAfter} GROUP BY record_extra ORDER BY min(record_timestamp) DESC;").each{
    String[] details = it.details.split(":")
    Long timestamp = it.timestamp
    String instanceId = details.replaceAll(".*=");
    String type = details.replaceAll(".*=");
    println "Found RUN for ${instanceId} ${type} ${timestamp}"
  }
  sql.rows("SELECT * FROM eucalyptus_records.records_logs WHERE record_class LIKE 'VM' AND record_extra LIKE 'user=admin:%' AND record_extra LIKE '%:state=TERMINATED:% AND UNIX_TIMESTAMP(record_timestamp)*1000 < ${notAfter} ORDER BY record_timestamp DESC;'").each{
    String[] details = it.details.split(":")
    Long timestamp = it.timestamp
    String instanceId = details.replaceAll(".*=");
    String type = details.replaceAll(".*=");
    println "Found TERMINATE for ${instanceId} ${type} ${timestamp}"
  }
  results.add( u )
}
db?.commit()
def class UserReportInfo {
  String userName;
  Integer vmCount = 0;
  Integer m1small = 0;
  Integer c1medium = 0;
  Integer m1large = 0;
  Integer m1xlarge = 0;
  Integer c1xlarge = 0;
  Integer m1smallTime = 0;
  Integer c1mediumTime = 0;
  Integer m1largeTime = 0;
  Integer m1xlargeTime = 0;
  Integer c1xlargeTime = 0;
}

