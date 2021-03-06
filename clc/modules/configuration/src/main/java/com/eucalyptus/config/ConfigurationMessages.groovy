/*************************************************************************
 * Copyright 2008 Regents of the University of California
 * Copyright 2009-2012 Ent. Services Development Corporation LP
 *
 * Redistribution and use of this software in source and binary forms,
 * with or without modification, are permitted provided that the
 * following conditions are met:
 *
 *   Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 *   Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer
 *   in the documentation and/or other materials provided with the
 *   distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE. USERS OF THIS SOFTWARE ACKNOWLEDGE
 * THE POSSIBLE PRESENCE OF OTHER OPEN SOURCE LICENSED MATERIAL,
 * COPYRIGHTED MATERIAL OR PATENTED MATERIAL IN THIS SOFTWARE,
 * AND IF ANY SUCH MATERIAL IS DISCOVERED THE PARTY DISCOVERING
 * IT MAY INFORM DR. RICH WOLSKI AT THE UNIVERSITY OF CALIFORNIA,
 * SANTA BARBARA WHO WILL THEN ASCERTAIN THE MOST APPROPRIATE REMEDY,
 * WHICH IN THE REGENTS' DISCRETION MAY INCLUDE, WITHOUT LIMITATION,
 * REPLACEMENT OF THE CODE SO IDENTIFIED, LICENSING OF THE CODE SO
 * IDENTIFIED, OR WITHDRAWAL OF THE CODE CAPABILITY TO THE EXTENT
 * NEEDED TO COMPLY WITH ANY SUCH LICENSES OR RIGHTS.
 ************************************************************************/

@GroovyAddClassUUID
package com.eucalyptus.config;

import com.eucalyptus.component.annotation.ComponentMessage
import edu.ucsb.eucalyptus.msgs.BaseMessage
import edu.ucsb.eucalyptus.msgs.EucalyptusData

import edu.ucsb.eucalyptus.msgs.GroovyAddClassUUID

@ComponentMessage(ConfigurationService.class)
public class ConfigurationMessage extends BaseMessage {}
public class ComponentInfoType extends EucalyptusData {
  String type;
  String partition;
  String name;
  String hostName;
  String fullName;
  String state;//really an enum
  String detail;
  public ComponentInfoType(){}
}

public class NodeComponentInfoType extends EucalyptusData {
  String name;
  String clusterName;  
  ArrayList<String> instances = new ArrayList<String>();
  public NodeComponentInfoType(){}
  public NodeComponentInfoType(String name, String clusterName){
    this.name = name; 
    this.clusterName = clusterName;
  }
}

public class RegisterComponentType extends ConfigurationMessage {
  String partition;
  String name;
  String host;
  Integer port;
  def RegisterComponentType() {}
  def RegisterComponentType(final String partition, final String name, final String host, final Integer port) {
    this.partition = partition;
    this.name = name;
    this.host = host;
    this.port = port;
  }
}
public class RegisterComponentResponseType extends ConfigurationMessage {}
public class DeregisterComponentType extends ConfigurationMessage {
  String name;
  String partition;
}
public class DeregisterComponentResponseType extends ConfigurationMessage {}

public class DescribeComponentsType extends ConfigurationMessage {
  Boolean verbose = Boolean.FALSE;
}
public class DescribeComponentsResponseType extends ConfigurationMessage {
  ArrayList<ComponentInfoType> registered = new ArrayList<ComponentInfoType>();
}
public class ModifyComponentAttributeType extends ConfigurationMessage {
  String partition;
  String name;
  String attribute; //{partition,state}
  String value;
}
public class ModifyComponentAttributeResponseType extends ConfigurationMessage {}

public class GetComponentLogsType extends DescribeComponentsType {}
public class GetComponentLogsResponseType extends DescribeComponentsResponseType {}
