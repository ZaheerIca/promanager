package com.tees.d3908727.pro_manager.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tees.d3908727.pro_manager.ui.models.ParseableProject
import com.tees.d3908727.pro_manager.ui.models.Project

@Composable
fun ProjectItemCard(
    project: ParseableProject,
    onClick: (ParseableProject) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 5.dp, end = 20.dp, start= 20.dp)
            .clickable { onClick(project) },
        color = Color.DarkGray,
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            
            // Project title
            Text(
                text = project.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 20.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Start Date
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Start Date: ",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                )
                Text(
                    text = project.startDate,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // End Date
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "End Date: ",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                )
                Text(
                    text = project.endDate,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}


@Preview
@Composable
fun ProjectItemCardPreview() {
    val project = ParseableProject(
        "1", "2", "First Prj",
        "If you know, you know",
        "2020-02-02", "2020-02-02", false, emptyList()
    )
    ProjectItemCard(project){}
}
