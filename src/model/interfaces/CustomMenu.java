package model.interfaces;

import model.Menu;

import java.util.Scanner;

public interface CustomMenu {
	void addItemMenu (Menu menu, Scanner tcl);
	void delItemMenu (Menu menu, Scanner tcl);
}
