package com.example.crms.restControllers;

import java.util.Collection;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.hateoas.ResourceSupport;

import com.example.crms.domain.Action;
import com.example.crms.domain.Call;

@XmlRootElement
public class CallActionRepresentation extends ResourceSupport
{
	@Valid
	private Call call;
	
	@Valid
	private Collection<Action> actions;
	
	public CallActionRepresentation() {}

	public Call getCall() {
		return call;
	}

	public void setCall(Call call) {
		this.call = call;
	}

	public Collection<Action> getActions() {
		return actions;
	}

	public void setActions(Collection<Action> actions) {
		this.actions = actions;
	}
	
	
}
