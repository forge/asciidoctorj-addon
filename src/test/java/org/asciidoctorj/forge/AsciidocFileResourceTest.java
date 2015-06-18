/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.asciidoctorj.forge;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.File;
import java.nio.file.Files;

import javax.inject.Inject;

import org.asciidoctor.ast.StructuredDocument;
import org.asciidoctorj.forge.resource.AsciidocFileResource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.arquillian.AddonDependencies;
import org.jboss.forge.arquillian.archive.AddonArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
@RunWith(Arquillian.class)
public class AsciidocFileResourceTest
{

   @Deployment
   @AddonDependencies
   public static AddonArchive getDeployment()
   {
      return ShrinkWrap.create(AddonArchive.class)
               .addBeansXML();
   }

   @Inject
   private ResourceFactory resourceFactory;

   @Test
   public void testFindResource() throws Exception
   {
      File tmpFile = File.createTempFile("document", ".adoc");
      tmpFile.deleteOnExit();
      Resource<?> resource = resourceFactory.create(tmpFile);
      Assert.assertThat(resource, instanceOf(AsciidocFileResource.class));
   }

   @Test
   public void testReadStructuredDocument() throws Exception
   {
      File tmpFile = File.createTempFile("document", ".adoc");
      Files.write(tmpFile.toPath(), "==Title\nHello World!".getBytes());
      tmpFile.deleteOnExit();
      AsciidocFileResource resource = resourceFactory.create(AsciidocFileResource.class, tmpFile);
      StructuredDocument document = resource.getStructuredDocument();
      Assert.assertNotNull(document);
      Assert.assertEquals(1, document.getParts().size());
   }

   @Test
   public void testConvertToHTML() throws Exception
   {
      File tmpFile = File.createTempFile("document", ".adoc");
      Files.write(tmpFile.toPath(), "Writing AsciiDoc is _easy_!".getBytes());
      tmpFile.deleteOnExit();
      AsciidocFileResource resource = resourceFactory.create(AsciidocFileResource.class, tmpFile);
      String html = resource.toHTML();
      Assert.assertEquals("<div class=\"paragraph\">\n<p>Writing AsciiDoc is <em>easy</em>!</p>\n</div>", html);
   }

}
