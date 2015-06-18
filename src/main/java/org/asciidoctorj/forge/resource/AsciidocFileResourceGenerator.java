/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.asciidoctorj.forge.resource;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import org.asciidoctor.Asciidoctor;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.resource.ResourceGenerator;

/**
 * {@link ResourceGenerator} implementation for {@link AsciidocFileResource}
 * 
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
@Singleton
public class AsciidocFileResourceGenerator implements ResourceGenerator<AsciidocFileResource, File>
{
   private Asciidoctor asciidoctor;

   @PostConstruct
   void init()
   {
      asciidoctor = Asciidoctor.Factory.create(getClass().getClassLoader());
   }

   @PreDestroy
   void dispose()
   {
      this.asciidoctor.shutdown();
      this.asciidoctor = null;
   }

   @Produces
   public Asciidoctor getAsciidoctor()
   {
      return asciidoctor;
   }

   @Override
   public boolean handles(Class<?> type, Object resource)
   {
      if (!(resource instanceof File))
         return false;
      File file = (File) resource;
      return (file.getName().endsWith(".adoc") || file.getName().endsWith(".ad"));
   }

   @SuppressWarnings("unchecked")
   @Override
   public <T extends Resource<File>> T getResource(ResourceFactory factory, Class<AsciidocFileResource> type,
            File resource)
   {
      return (T) new AsciidocFileResourceImpl(factory, resource, asciidoctor);
   }

   @Override
   public <T extends Resource<File>> Class<?> getResourceType(ResourceFactory factory,
            Class<AsciidocFileResource> type, File resource)
   {
      return AsciidocFileResource.class;
   }

}
