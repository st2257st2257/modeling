from mpl_toolkits.mplot3d import Axes3D
import xlrd, xlwt
import matplotlib.pyplot as plt
import numpy as np
import math

#data
rb = xlrd.open_workbook("C://MIPT//PROG//Kotlin//HW//task_2//task2.xls")
sheet = rb.sheet_by_index(0)
print(sheet.row_values(0))


#                           y      x                     delta y                   delta x
X=[[float(sheet.row_values(0+i)[0]) for i in range(1000)]]
Y=[[float(sheet.row_values(0+i)[2]) for i in range(1000)]]
Z=[[float(sheet.row_values(0+i)[4]) for i in range(1000)]]

x = np.linspace(-np.pi, np.pi, 50)
y = x
z = np.cos(x)
fig = plt.figure()
ax = fig.gca(projection='3d')
ax.view_init(30, -89)
ax = fig.add_subplot(111, projection='3d')
ax.plot(X[0], Y[0], Z[0]) #, label='parametric curve')