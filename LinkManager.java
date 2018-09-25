package com.easyLink.easyLink;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkManager {

//	@Autowired
//	private LinkRepository linkRepository;
	
	private List<Link> links = new ArrayList<>(Arrays.asList(
			new Link("links", "www.google.com"), 
			new Link("odo", "odo.lv"),
			new Link("ss", "www.youtube.com"))
			);

	public List<Link> getAllLinks() {
		return links;
//		List<Link> liste = new ArrayList<Link>();
//		linkRepository.findAll()
//		.forEach(liste::add);
//		return liste;
	}

	public Link getLink(String id) {
		return links.stream().filter(l -> l.getId().equals(id)).findFirst().get();
//		return linkRepository.findById(id).get();
	}

	public void addLink(Link link) {
		link = new Link(link.getId(), link.getURL());
		links.add(link);
//		linkRepository.save(link);

	}
	
	public void addLink(String id, String URL) {
		Link link = new Link(id, URL);
		links.add(link);
//		linkRepository.save(link);
	}

	public void updateLink(String id, Link link) {
		for(Link item: links) {
			if(item.getId().equals(id)) {
				item = link;
				return;
			}
		}
		
//		linkRepository.save(link);
		
	}

	public void deleteLink(String id) {
		links.removeIf(l -> l.getId().equals(id));
//		linkRepository.deleteById(id);
	}
	
}
