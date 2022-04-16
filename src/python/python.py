from mpl_toolkits.mplot3d import Axes3D
import xlrd, xlwt
import matplotlib.pyplot as plt
import numpy as np
import math

# read data
rb001 = xlrd.open_workbook("C://MIPT//PROG//Kotlin//HW//task_2//f_001.xls")
sheet001 = rb001.sheet_by_index(0)

rb002 = xlrd.open_workbook("C://MIPT//PROG//Kotlin//HW//task_2//f_002.xls")
sheet002 = rb002.sheet_by_index(0)

rb004 = xlrd.open_workbook("C://MIPT//PROG//Kotlin//HW//task_2//f_004.xls")
sheet004 = rb004.sheet_by_index(0)

rb008 = xlrd.open_workbook("C://MIPT//PROG//Kotlin//HW//task_2//f_008.xls")
sheet008 = rb008.sheet_by_index(0)

rb016 = xlrd.open_workbook("C://MIPT//PROG//Kotlin//HW//task_2//f_016.xls")
sheet016 = rb016.sheet_by_index(0)

rb01 = xlrd.open_workbook("C://MIPT//PROG//Kotlin//HW//task_2//f_01.xls")
sheet01 = rb01.sheet_by_index(0)

rb09 = xlrd.open_workbook("C://MIPT//PROG//Kotlin//HW//task_2//f_09.xls")
sheet09 = rb09.sheet_by_index(0)

rbsheet = [rb001, rb002, rb004, rb008, rb016, rb01, rb09]
sheet = [sheet001, sheet002, sheet004, sheet008, sheet01, sheet016, sheet09]

const   = [50000, 25000, 12500, 6250, 5000, 3125, 625]
const_2 = [80,40,20,10,8,5,1]
h = [0.001, 0.002, 0.004, 0.008, 0.010,  0.016, 0.090]

#                           y      x                     delta y                   delta x
X=[[float(sheet[j].row_values(0+i)[0]) for i in range(const[j])] for j in range(len(sheet))]
Y=[[float(sheet[j].row_values(0+i)[2]) for i in range(const[j])] for j in range(len(sheet))]
Z=[[float(sheet[j].row_values(0+i)[4]) for i in range(const[j])] for j in range(len(sheet))]


# check data

check = [[ [X[j][i*const_2[j]], Y[j][i*const_2[j]], Z[j][i*const_2[j]]] for i in range(625)] for j in range(len(sheet))]

check_res = [[ ((X[j][i*const_2[j]] - X[0][i*80])**2 +
               (Y[j][i*const_2[j]] - Y[0][i*80])**2 +
               (Z[j][i*const_2[j]] - Z[0][i*80])**2)**(0.5)  for i in range(625)] for j in range(len(sheet))]


fig = plt.figure()
ax = fig.gca(projection='3d')
ax.view_init(30, -89)
ax = fig.add_subplot(111, projection='3d')

plt.figure()
plt.plot([i for i in range(625)], check_res[4])
_first_litter_value, s_legend = plt.subplots()
s_legend.set_xlabel("Number of step") 
s_legend.set_ylabel("Deviation") 

l_array = []
colors = ["BLACK","RED","GREEN","BLUE","YELLOW", "VIOLET", "CORAL", "GRAY"]
for i in range(len(sheet)):
    ax.plot(X[i], Y[i], Z[i])
    l, = plt.plot([i for i in range(625)], check_res[i], color = colors[i])
    l_array.append(l)

plt.legend(l_array, ["h = 0.001", "h = 0.002", "h = 0.004", "h = 0.008", "h = 0.010",
                     "h = 0.016", "h = 0.090"], loc='upper left', fontsize=9)

