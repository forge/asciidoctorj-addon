/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.asciidoctorj.forge.resource;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.ast.Document;
import org.jboss.forge.addon.resource.AbstractFileResource;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.resource.ResourceFactory;

/**
 * {@link AsciidocFileResource} implementation
 * 
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
class AsciidocFileResourceImpl extends AbstractFileResource<AsciidocFileResource> implements AsciidocFileResource
{
   private final Asciidoctor asciidoctor;

   public AsciidocFileResourceImpl(ResourceFactory factory, File resource, Asciidoctor asciidoctor)
   {
      super(factory, resource);
      this.asciidoctor = asciidoctor;
   }

   @Override
   public Resource<File> createFrom(File file)
   {
      return new AsciidocFileResourceImpl(getResourceFactory(), file, asciidoctor);
   }

   @Override
   protected List<Resource<?>> doListResources()
   {
      return Collections.emptyList();
   }

   @Override
   public Document getDocument()
   {
      return getDocument(Collections.<String, Object> emptyMap());
   }

   @Override
   public Document getDocument(Map<String, Object> options)
   {
      return getAsciidoctor().loadFile(getUnderlyingResourceObject(), options);
   }

   @Override
   public String toHTML()
   {
      Options options = new Options();
      options.setToFile(false);
      return getAsciidoctor().convertFile(getUnderlyingResourceObject(), options);
   }

   private Asciidoctor getAsciidoctor()
   {
      return asciidoctor;
   }
}
