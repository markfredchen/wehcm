package com.mcworkshop.wehcm.web.rest.resource;

import com.mcworkshop.wehcm.core.domain.DomainObject;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by markfredchen on 3/6/15.
 */
abstract public class BaseResource extends ResourceSupport {

    abstract public <T extends DomainObject> T toEntity();
}
