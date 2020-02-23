package com.adityamhatre.firebase_checker.firebase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;


@FunctionalInterface
interface OnChildAdded {
	void onChildAdded(DataSnapshot snapshot);
}

@FunctionalInterface
interface OnChildChanged {
	void onChildChanged(DataSnapshot snapshot);
}

class SimplifiedChildEventListener implements ChildEventListener {

	private OnChildAdded onChildAdded;
	private OnChildChanged onChildChanged;

	SimplifiedChildEventListener(OnChildAdded onChildAdded, OnChildChanged onChildChanged) {
		this.onChildAdded = onChildAdded;
		this.onChildChanged = onChildChanged;
	}

	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		onChildAdded.onChildAdded(snapshot);
	}

	@Override
	public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
		onChildChanged.onChildChanged(snapshot);
	}

	@Override
	public void onChildRemoved(DataSnapshot snapshot) {

	}

	@Override
	public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

	}

	@Override
	public void onCancelled(DatabaseError error) {

	}
}