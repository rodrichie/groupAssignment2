package com.example.groupassignment2.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.groupassignment2.R
import com.example.groupassignment2.components.datastore.RoutineData
import com.example.groupassignment2.components.datastore.RoutineViewModel
import com.example.groupassignment2.models.IconType
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Routines(
    navController: NavHostController
){

    val context =LocalContext.current
    //val scope = rememberCoroutineScope()
    val dataStore =RoutineViewModel(context)
    dataStore.loadData()


    val title = " "

    val iconsList = listOf(IconType.PainterIcon(painterResource(id = R.drawable.filter_left)))
    Scaffold(
        topBar = {
            MyToolbar(iconsList1 = emptyList(), title , iconsList2 = iconsList, navController)
        },
        content ={

            if(
                dataStore.routineDataList.isEmpty()
            ){
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
                    ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center

                ) {

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){

                    Image(
                        painter = painterResource(id = R.drawable.routines),
                        contentDescription = null,
                        modifier = Modifier.size(120.dp)
                    )

                        Text(
                            text = "No Routines!",
                            style = MaterialTheme.typography.h6,
                        )
                        Text(
                            text = "Click the '+' button to get started",
                            style = MaterialTheme.typography.subtitle1,
                            color = Color.Gray
                        )
                    }
                }
            }
        }else{
            Column{
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .weight(1f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth().height(50.dp)
                        .background(colorResource(id = R.color.settingsRow)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Active",
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                for (routineData in dataStore.routineDataList) {


                    val name = routineData.routineName
                    val time = routineData.timeOfRunning
                    val notification = routineData.notificationText

                    val recordIndex = dataStore.routineDataList.indexOf(routineData)


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.notify),
                            contentDescription = null,
                            modifier = Modifier.padding(start = 16.dp)
                        )

                        Column(
                            modifier = Modifier.padding(start = 16.dp)
                        ) {

                            Text(
                                text = "${routineData.routineName}",
                                fontWeight = FontWeight.Bold

                            )
                            Text(
                                text = "Last Run: ${routineData.timeOfRunning}",
                                style = MaterialTheme.typography.subtitle1,
                                color = Color.Gray
                            )
                        }
                        Row(
                            modifier = Modifier
                                .padding(end= 16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ){
                            Button(onClick = {
                                navController.currentBackStackEntry?.arguments?.putString("name", name)
                                navController.currentBackStackEntry?.arguments?.putString("time", time)
                                navController.currentBackStackEntry?.arguments?.putString("notification", notification)
                                navController.currentBackStackEntry?.arguments?.putInt("recordIndex", recordIndex)
                                navController.navigate("editRoutine")

                            }) {
                                Text(text = "Edit")
                            }
                            Spacer(Modifier.width(8.dp))
                            Button(onClick = {
                                dataStore.deleteData(recordIndex)
                            }) {
                                Text(text = "Delete")
                            }
                        }


                    }

                }
            }
        }}

                 }
        ,
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                FloatingActionButton(
                    onClick = { navController.navigate("createRoutine")},
                    modifier = Modifier.align(Alignment.BottomEnd),
                    contentColor = Color.White,
                    backgroundColor = Color.Blue

                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")                }
            }

        }

    )
}
