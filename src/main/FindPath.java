package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class FindPath {

    int maxCol = 0;
    int maxRow = 0;

    private void main(String fileName) throws IOException {
	int[][] skiMap = readFile(fileName);
	List<LinkedList<Integer>> result = new ArrayList<LinkedList<Integer>>();
	for (int y = 1; y < maxRow - 1; y++) {
	    for (int x = 1; x < maxCol - 1; x++) {
		traverseMap(y, x, skiMap, generateVisitedMap(),
			new LinkedList<Integer>(), result);
	    }
	}
	sortAndPrintResult(result);
    }

    private int calculateSlope(LinkedList<Integer> que) {

	return que.getLast() - que.getFirst();
    }

    private void sortAndPrintResult(List<LinkedList<Integer>> result) {
	Collections.sort(result, new Comparator<LinkedList<Integer>>() {

	    @Override
	    public int compare(LinkedList<Integer> firstQueue,
		    LinkedList<Integer> secondQueue) {

		if (secondQueue.size() == firstQueue.size()) {
		    return calculateSlope(secondQueue)
			    - calculateSlope(firstQueue);
		} else {
		    return secondQueue.size() - firstQueue.size();
		}

	    }
	});

	int line = 0;
	for (LinkedList<Integer> que : result) {
	    StringBuffer sb = new StringBuffer();
	    int len = que.size();
	    while (!que.isEmpty()) {
		sb.append(que.pollLast()).append(",");
	    }
	    System.out.println("length=" + len + ", result=" + sb.toString());

	    line++;
	    if (line > 5) {
		return;
	    }
	}

    }

    private boolean[][] generateVisitedMap() {
	boolean[][] visitedMap = new boolean[maxRow][maxCol];
	for (int y = 0; y < maxRow; y++) {
	    for (int x = 0; x < maxCol; x++) {
		visitedMap[y][x] = false;
	    }
	}
	return visitedMap;

    }

    private void traverseMap(int curY, int curX, int[][] skiMap,
	    boolean[][] visitedMap, LinkedList<Integer> stack,
	    List<LinkedList<Integer>> result) {
	Integer curVal = skiMap[curY][curX];

	visitedMap[curY][curX] = true;

	stack.push(curVal);

	if (curX <= 0 || curY <= 0 || curX >= maxCol - 1 || curY >= maxRow - 1) {
	    LinkedList<Integer> stackClone = new LinkedList<Integer>();

	    stackClone.addAll(stack);
	    stackClone.pop();
	    result.add(stackClone);

	} else {

	    if ((skiMap[curY][curX - 1] < curVal)
		    && (!visitedMap[curY][curX - 1])) {
		traverseMap(curY, curX - 1, skiMap, visitedMap, stack, result);
	    }// left;
	    if (skiMap[curY][curX + 1] < curVal
		    && (!visitedMap[curY][curX + 1])) {
		traverseMap(curY, curX + 1, skiMap, visitedMap, stack, result);
	    }// right;
	    if (skiMap[curY - 1][curX] < curVal
		    && (!visitedMap[curY - 1][curX])) {
		traverseMap(curY - 1, curX, skiMap, visitedMap, stack, result);
	    }// up;
	    if (skiMap[curY + 1][curX] < curVal
		    && (!visitedMap[curY + 1][curX])) {
		traverseMap(curY + 1, curX, skiMap, visitedMap, stack, result);
	    }// down;
	}
	visitedMap[curY][curX] = false;
	stack.pop();
    }

    private int[][] generateMapMinusOne(int row, int col) {
	int[][] result = new int[row][col];
	for (int y = 0; y < row; y++) {
	    for (int x = 0; x < col; x++) {
		result[y][x] = -1;
	    }
	}
	return result;
    }

    private int[][] readFile(String filename) throws IOException {
	File file = new File(filename);
	try (BufferedReader br = new BufferedReader(new FileReader(file))) {

	    {
		String line = br.readLine();
		String[] arrNumber = line.split(" ");
		maxRow = Integer.parseInt(arrNumber[0]) + 2;
		maxCol = Integer.parseInt(arrNumber[1]) + 2;
	    }
	    int row = 0;
	    int[][] result = generateMapMinusOne(maxRow, maxCol);

	    for (String line; (line = br.readLine()) != null; row++) {
		String[] arrNumber = line.split(" ");

		for (int col = 0; col < arrNumber.length; col++) {
		    result[1 + row][1 + col] = Integer.parseInt(arrNumber[col]);
		}
	    }
	    return result;
	}

    }

    /**
     * @param args
     */
    public static void main(String[] args) {

	try {
	    new FindPath().main(args[0]);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
