package org.scribe.services;

import javax.xml.bind.*;

public class DatatypeConverterEncoder extends Base64Encoder
{
  
  public String encode(byte[] bytes)
  {
    return DatatypeConverter.printBase64Binary(bytes);
  }

  
  public String getType()
  {
    return "DatatypeConverter";
  }
}
