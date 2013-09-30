package org.scribe.extractors;

import org.scribe.model.*;

/**
 * Simple command object that extracts a {@link Token} from a String
 * 
 * @author Pablo Fernandez
 */
public interface RequestTokenExtractor
{
  /**
   * Extracts the request token from the contents of an Http ResponseHttpImpl
   *  
   * @param response the contents of the response
   * @return OAuth access token
   */
  public Token extract(String response);
}
