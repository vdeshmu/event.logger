package com.log.event.logger.entity;

import java.io.Serializable;

import com.log.event.logger.entity.enums.State;

public class Event implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5261633253453760607L;
	private String id;
	private String type;
	private String host;
	private String timeStamp;
	private int timeDifference;
	private boolean alert;
	private State state;
	
	public Event(String id,String type,String host,String timeStamp) {
		this.id = id;
		this.type = type;
		this.host = host;
		this.timeStamp = timeStamp;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public boolean isAlert() {
		return alert;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (timeStamp == null) {
			if (other.timeStamp != null)
				return false;
		} else if (!timeStamp.equals(other.timeStamp))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public int getTimeDifference() {
		return Integer.parseInt(timeDifference+"");
	}

	public void setTimeDifference(float f) {
		this.timeDifference = (int) Math.abs(f);
		if(Math.abs(f)>=4) {
			alert = true;
		}
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", type=" + type + ", host=" + host + ", timeStamp=" + timeStamp
				+ ", timeDifference=" + timeDifference + ", alert=" + alert + ", state=" + state+"]";
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
}
