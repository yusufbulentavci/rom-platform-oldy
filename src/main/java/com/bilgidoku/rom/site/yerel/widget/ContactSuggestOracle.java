package com.bilgidoku.rom.site.yerel.widget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gwt.user.client.ui.SuggestOracle;

/**
 * @author James Heggs - jheggs@axonbirch.com
 * 
 */
public class ContactSuggestOracle extends SuggestOracle {

	private List<ContactMultiWordSuggestion> personSuggestions = null;

	@Override
	public void requestSuggestions(Request request, Callback callback) {
		Response resp = new Response(matchingPeople(request.getQuery(), request.getLimit()));
		callback.onSuggestionsReady(request, resp);
	}

	/**
	 * 
	 * @param query
	 *            The current text being entered into the suggest box
	 * @param limit
	 *            The maximum number of results to return
	 * @return A collection of people suggestions that match.
	 */
	public Collection<ContactMultiWordSuggestion> matchingPeople(String query, int limit) {
		List<ContactMultiWordSuggestion> matchingPeople = new ArrayList<ContactMultiWordSuggestion>(limit);

		// only begin to search after the user has type two characters
		if (query.length() >= 2) {
			String prefixToMatch = query.toLowerCase();

			int i = 0;
			int s = personSuggestions.size();

			// Skip forward over all the names that don't match at the beginning
			// of the array.			
			while (i < s && !personSuggestions.get(i).getReplacementString().toLowerCase().startsWith(prefixToMatch)) {
				i++;
			}

			// Now we are at the start of the block of matching names. Add
			// matching names till we
			// run out of names, stop finding matches, or have enough matches.
			int count = 0;

			while (i < s && personSuggestions.get(i).getReplacementString().toLowerCase().startsWith(prefixToMatch) && count < limit) {
				matchingPeople.add(personSuggestions.get(i));
				i++;
				count++;
			}

		}

		return matchingPeople;
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean add(ContactMultiWordSuggestion o) {
		if (personSuggestions == null) {
			personSuggestions = new ArrayList<ContactMultiWordSuggestion>();
		}
		return personSuggestions.add(o);
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(Object o) {
		if (personSuggestions != null) {
			return personSuggestions.remove(o);
		}

		return false;
	}

	public boolean isDisplayStringHTML() {
		return true;
	}
}