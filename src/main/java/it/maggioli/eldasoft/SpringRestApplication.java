/*
 * Created on 01/giu/2017
 *
 * Copyright (c) Maggioli S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di Maggioli S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con Maggioli.
 */
package it.maggioli.eldasoft;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

/**
 * Si configura l'applicazione REST mediante Jersey e la parte JSON mediante Jackson.
 *
 * @author Stefano.Sabbadin
 */
public class SpringRestApplication extends ResourceConfig {

  /**
   * Register JAX-RS application components.
   */
  public SpringRestApplication() {
    register(RequestContextFilter.class);
    register(MultiPartFeature.class);
    register(JacksonFeature.class);
  }
}
