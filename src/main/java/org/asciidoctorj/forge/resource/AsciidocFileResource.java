/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.asciidoctorj.forge.resource;

import java.util.Map;

import org.asciidoctor.ast.Document;
import org.jboss.forge.addon.resource.FileResource;

/**
 * An Asciidoc resource
 * 
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
public interface AsciidocFileResource extends FileResource<AsciidocFileResource>
{
   /**
    * Reads and creates structured document containing header and content chunks. By default it dig only one level down
    * but it can be tweak by setting STRUCTURE_MAX_LEVEL option.
    * 
    * @return structured document.
    */
   Document getDocument();

   /**
    * Reads and creates structured document containing header and content chunks. By default it dig only one level down
    * but it can be tweak by setting STRUCTURE_MAX_LEVEL option.
    * 
    * @param options a Hash of options to control processing (default: {}).
    * @return structured document.
    */
   Document getDocument(Map<String, Object> options);

   /**
    * Renders the Asciidoc file to HTML
    * 
    * @return the HTML version of this Asciidoc file
    */
   String toHTML();

}
